package com.example.petadoptionbatch.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
@ConfigurationProperties("batch.pet.file-manager")
public class FileManagerProperties {

    private String filePath;
    private Integer chuckSize;
}
