package com.example.petadoptionbatch.components;

import com.example.petadoptionbatch.data.PetDto;
import com.example.petadoptionbatch.data.batch.PetStageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;

@StepScope
@Slf4j
public class DataItemProcessor implements ItemProcessor<PetStageEntity, PetDto> {

    @Override
    public PetDto process(PetStageEntity item) {
        log.info("processing data {}", item.getPetId());
        return PetDto.builder()
                .petId(item.getPetId())
                .petType(getPetType(item.getPetType()))
                .petAgeYear(Integer.parseInt(item.getPetAgeYear()))
                .petAgeMonth(Integer.parseInt(item.getPetAgeMonth()))
                .sterilisedFlag(getFlagValue(item.getSterilisedFlag()))
                .vaccinatedFlag(getFlagValue(item.getVaccinatedFlag()))
                .ownedFlag(getFlagValue(item.getOwnedFlag()))
                .build();
    }

    private String getPetType(String petType) {
        return switch (petType) {
            case "B" -> "Bird";
            case "C" -> "Cat";
            case "D" -> "Dog";
            case "R" -> "Rabbit";
            default -> "Others";
        };
    }

    private boolean getFlagValue(String flag) {
        if (flag.equals("Y")) {
            return true;
        } else {
            return false;
        }
    }

}
