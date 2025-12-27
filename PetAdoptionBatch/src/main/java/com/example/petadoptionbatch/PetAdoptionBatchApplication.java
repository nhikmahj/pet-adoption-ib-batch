package com.example.petadoptionbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PetAdoptionBatchApplication implements CommandLineRunner {

    private final JobLauncher jobLauncher;

    private final Job petAdoptionBatchJob;

    public PetAdoptionBatchApplication(JobLauncher jobLauncher, Job petAdoptionBatchJob) {
        this.jobLauncher = jobLauncher;
        this.petAdoptionBatchJob = petAdoptionBatchJob;
    }

    public static void main(String[] args) {
        SpringApplication.run(PetAdoptionBatchApplication.class, args);
    }

    public void run(String[] args) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
    JobParametersInvalidException, JobRestartException {
        log.info("----------------Starting Batch Interface for {} -----------------", "petAdoptionInboundBatch");
        var jobParams = new JobParametersBuilder().addString("petAdoptionInboundBatch",
                String.valueOf(System.currentTimeMillis())).toJobParameters();
        jobLauncher.run(petAdoptionBatchJob, jobParams);
        log.info("----------------Batch Processing Completed for {} -----------------", "petAdoptionInboundBatch");
    }

}