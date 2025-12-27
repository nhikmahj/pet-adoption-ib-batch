package com.example.petadoptionbatch.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.petadoptionbatch.data.pet",
        entityManagerFactoryRef = "petEntityManagerFactory",
        transactionManagerRef = "petTransactionManager"
)
public class DataSourceConfig {

    @Bean("petConfig")
    @ConfigurationProperties("spring.datasource.pet")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean("petDataSource")
    public HikariDataSource petDataSource(@Qualifier("petConfig") HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "petEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean petEntityManagerFactory(
            @Autowired EntityManagerFactoryBuilder builder,
            @Qualifier("petDataSource") DataSource petDataSource
    ) {
        return builder.dataSource(petDataSource)
                .packages("com.example.petadoptionbatch.data.pet")
                .build();
    }

    @Bean(name = "petTransactionManager")
    public PlatformTransactionManager petTransactionManager(
            @Qualifier("petEntityManagerFactory") LocalContainerEntityManagerFactoryBean petEntityManagerFactory) throws Exception {

        EntityManagerFactory emf = petEntityManagerFactory.getObject();
        if (emf == null) {
            throw new Exception("petEntityManagerFactory is null");
        }
        return new JpaTransactionManager(emf);
    }
}
