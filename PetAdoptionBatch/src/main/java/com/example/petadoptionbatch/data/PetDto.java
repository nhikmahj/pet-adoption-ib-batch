package com.example.petadoptionbatch.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDto {

    private String petId;
    private String petType;
    private int petAgeYear;
    private int petAgeMonth;
    private boolean sterilisedFlag;
    private boolean vaccinatedFlag;
    private boolean ownedFlag;
}
