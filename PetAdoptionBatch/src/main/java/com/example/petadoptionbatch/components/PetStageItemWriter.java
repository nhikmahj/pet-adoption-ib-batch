package com.example.petadoptionbatch.components;

import com.example.petadoptionbatch.data.batch.PetStageEntity;
import com.example.petadoptionbatch.data.batch.PetStageRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@StepScope
@Slf4j
public class PetStageItemWriter implements ItemWriter<PetStageEntity> {

    private final PetStageRepository petStageRepository;


    public PetStageItemWriter(PetStageRepository petStageRepository) {
        this.petStageRepository = petStageRepository;
    }

    @Override
    public void write(Chunk<? extends PetStageEntity> items) throws Exception {
        items.getItems().forEach(this::process);
    }

    private void process(PetStageEntity petStageEntity) {
        petStageEntity.setBatchStatus("PROCESSING");
        log.info("saving into stage table");
        petStageRepository.save(petStageEntity);
    }
}
