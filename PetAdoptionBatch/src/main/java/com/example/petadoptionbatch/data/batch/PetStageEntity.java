package com.example.petadoptionbatch.data.batch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_stage_pet")
public class PetStageEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_batch_id")
    private Long petBatchId;

    @Column(name = "batch_status")
    private String batchStatus;

    @Column(name = "pet_id")
    private String petId;

    @Column(name = "pet_type")
    private String petType;

    @Column(name = "pet_age_year")
    private String petAgeYear;

    @Column(name = "pet_age_month")
    private String petAgeMonth;

    @Column(name = "sterilised_flag")
    private String  sterilisedFlag;

    @Column(name = "vaccinated_flag")
    private String vaccinatedFlag;

    @Column(name = "owned_flag")
    private String ownedFlag;

}
