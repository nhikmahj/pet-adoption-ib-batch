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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.petadoptionbatch.data.batch"
)
public class BatchSourceConfig {

    @Bean("batchConfig")
    @Primary
    @ConfigurationProperties("spring.datasource.batch")
    public HikariConfig batchHikariConfig() {
        return new HikariConfig();
    }

    @Bean("dataSource")
    @Primary
    public HikariDataSource batchDataSource(@Qualifier("batchConfig") HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Autowired EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource batchDataSource
    ) {

        return builder.dataSource(batchDataSource).packages("com.example.petadoptionbatch.data.batch").build();
    }

    @Bean("transactionManager")
    @Primary
    public PlatformTransactionManager dataSourceTransactionManager(
            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean
                                                                    entityManagerFactory) throws Exception {

        if (entityManagerFactory == null) {
            throw new Exception("entityManagerFactory is null");
        } else {
            EntityManagerFactory emf = entityManagerFactory.getObject();
            if (emf == null) {
                throw new Exception("entityManagerFactory is null");
            } else {
                return new JpaTransactionManager(emf);
            }
        }
    }
}
