package com.example.petadoptionbatch.data.pet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_pet_profile")
public class PetEntity {

    @Column(name = "pet_id")
    @Id
    private String petId;

    @Column(name = "pet_type")
    private String petType;

    @Column(name = "pet_age_year")
    private int petAgeYear;

    @Column(name = "pet_age_month")
    private int petAgeMonth;

    @Column(name = "sterilised_flag")
    private boolean sterilisedFlag;

    @Column(name = "vaccinated_flag")
    private boolean vaccinatedFlag;

    @Column(name = "owned_flag")
    private boolean ownedFlag;
}
