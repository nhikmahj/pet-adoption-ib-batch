package com.example.petadoptionbatch.components;

import com.example.petadoptionbatch.data.PetDto;
import com.example.petadoptionbatch.data.batch.PetStageEntity;
import com.example.petadoptionbatch.data.batch.PetStageRepository;
import com.example.petadoptionbatch.data.pet.PetEntity;
import com.example.petadoptionbatch.data.pet.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;


@StepScope
@Slf4j
public class DataItemWriter implements ItemWriter<PetDto> {

    private final ModelMapper modelMapper;
    private final PetRepository petRepository;
    private final PetStageRepository petStageRepository;


    public DataItemWriter(ModelMapper modelMapper, PetRepository petRepository, PetStageRepository petStageRepository) {
        this.modelMapper = modelMapper;
        this.petRepository = petRepository;
        this.petStageRepository = petStageRepository;
    }


    @Override
    public void write(Chunk<? extends PetDto> items) throws Exception {
        items.getItems().forEach(this::process);
    }

    private void process(PetDto petDto) {
        PetEntity petEntity = new PetEntity();
        modelMapper.map(petDto, petEntity);
        log.info("saving data into database");
        petRepository.save(petEntity);
        updateStageStatus(petDto.getPetId());
    }

    private void updateStageStatus(String petId) {
        PetStageEntity petStageEntity = petStageRepository.findByPetIdAndBatchStatus(petId, "PROCESSING");
        petStageEntity.setBatchStatus("COMPLETED");
        petStageRepository.save(petStageEntity);
    }
}
