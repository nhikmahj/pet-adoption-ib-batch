package com.example.petadoptionbatch.data.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetStageRepository extends JpaRepository<PetStageEntity,String> {

    List<PetStageEntity> findByBatchStatus(String status);

    PetStageEntity findByPetIdAndBatchStatus(String petId, String status);
}
