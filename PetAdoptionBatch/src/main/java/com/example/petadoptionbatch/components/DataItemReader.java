package com.example.petadoptionbatch.components;

import com.example.petadoptionbatch.data.batch.PetStageEntity;
import com.example.petadoptionbatch.data.batch.PetStageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@StepScope
@Slf4j
public class DataItemReader extends AbstractItemCountingItemStreamItemReader<PetStageEntity> {
    
    private final Object lock = new Object();
    private List<PetStageEntity> results;
    private final PetStageRepository petStageRepository;
    private int current;

    public DataItemReader(PetStageRepository petStageRepository) {
        this.petStageRepository = petStageRepository;
    }
    
    private List<PetStageEntity> doSearch() {
        List<PetStageEntity> petStageEntities = petStageRepository.findByBatchStatus("PROCESSING");
        log.info("staging data size {}", petStageEntities.size());
        
        if (petStageEntities.isEmpty()) {
            log.info("No records in stage table");
            return Collections.emptyList();
        }
        
        return petStageEntities;
    }

    @Override
    protected PetStageEntity doRead()  {
        synchronized (lock) {
            if (results == null) {
                current = 0;
                results = doSearch();
            }
            
            if (!CollectionUtils.isEmpty(results) && current < results.size()) {
                PetStageEntity petStageEntity = results.get(current);
                current++;
                return petStageEntity;
            } else {
                return null;
            }
        }
    }

    @Override
    protected void doOpen() {
        setName(this.getClass().getName());
    }

    @Override
    protected void doClose() {
        synchronized (lock) {
            current = 0;
            results = null;
        }
    }
}
