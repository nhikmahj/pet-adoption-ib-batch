package com.example.petadoptionbatch.config;

import com.example.petadoptionbatch.components.DataItemProcessor;
import com.example.petadoptionbatch.components.DataItemReader;
import com.example.petadoptionbatch.components.DataItemWriter;
import com.example.petadoptionbatch.components.PetStageItemWriter;
import com.example.petadoptionbatch.data.PetDto;
import com.example.petadoptionbatch.data.batch.PetStageEntity;
import com.example.petadoptionbatch.data.batch.PetStageRepository;
import com.example.petadoptionbatch.data.pet.PetRepository;
import com.example.petadoptionbatch.properties.FileManagerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class PetBatchConfig {

    private final FileManagerProperties fileManagerProperties;
    private final PetRepository petRepository;
    private final PetStageRepository petStageRepository;
    private final ModelMapper modelMapper;
    private Integer counter = 1;

    @Bean
    public Job petBatchJob(JobRepository jobRepository, Step stepOneReadFile, Step stepTwoInsertDb) {

        return new JobBuilder("petAdoptionInboundBatch", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepOneReadFile)
                .next(stepTwoInsertDb)
                .build();
    }

    @Bean
    public Step stepOneReadFile(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                ItemReader<PetStageEntity> petInboundReader, ItemWriter<PetStageEntity> petStageItemWriter) {
        return new StepBuilder("stepOneReadFile", jobRepository)
                .<PetStageEntity,PetStageEntity>chunk(fileManagerProperties.getChuckSize(),transactionManager)
                .reader(petInboundReader)
                .writer(petStageItemWriter)
                .build();
    }

    @Bean
    public ItemReader<PetStageEntity> petInboundReader() {
        String fileName = "petAdoptionInboundBatch.txt";
        FlatFileItemReader<PetStageEntity> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(fileManagerProperties.getFilePath() + "/" + fileName));
        log.info("reading file");
        reader.setLineMapper((line, lineNumber) -> {
            String petId = line.substring(0,5).trim();
            String petType = line.substring(5,6).trim();
            String petAgeYear = line.substring(6,8).trim();
            String petAgeMonth = line.substring(8,10).trim();
            String sterilised = line.substring(10,11).trim();
            String vaccinated = line.substring(11,12).trim();
            String owned = line.substring(12,13).trim();
            PetStageEntity petStageEntity = new PetStageEntity();
            petStageEntity.setPetId(petId);
            petStageEntity.setPetType(petType);
            petStageEntity.setPetAgeYear(petAgeYear);
            petStageEntity.setPetAgeMonth(petAgeMonth);
            petStageEntity.setSterilisedFlag(sterilised);
            petStageEntity.setVaccinatedFlag(vaccinated);
            petStageEntity.setOwnedFlag(owned);
            return petStageEntity;
        });
        log.info("reading next line {}", counter);
        counter++;
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    @StepScope
    public ItemWriter<PetStageEntity> petStageItemWriter() {
        return new PetStageItemWriter(petStageRepository);
    }

    @Bean
    public Step stepTwoInsertDb(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                ItemReader<PetStageEntity> petItemReader,
                                ItemProcessor<PetStageEntity, PetDto> petItemProcessor,
                                ItemWriter<PetDto> petItemWriter) {
        return new StepBuilder("stepTwoInsertDb", jobRepository)
                .<PetStageEntity,PetDto>chunk(fileManagerProperties.getChuckSize(),transactionManager)
                .reader(petItemReader)
                .processor(petItemProcessor)
                .writer(petItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public DataItemReader petItemReader() {
        return new DataItemReader(petStageRepository);
    }

    @Bean
    @StepScope
    public DataItemProcessor petItemProcessor() {
        return new DataItemProcessor();
    }

    @Bean
    @StepScope
    public DataItemWriter petItemWriter() {
        return new DataItemWriter(modelMapper, petRepository, petStageRepository);
    }
}
