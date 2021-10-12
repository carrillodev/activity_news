package com.example.news.configuration;

import com.example.news.domain.ports.api.NewServicePort;
import com.example.news.domain.ports.spi.NewPersistencePort;
import com.example.news.domain.service.NewServiceImpl;
import com.example.news.infraestructure.adapters.NewJpaAdapter;
import com.example.news.storage.StorageProperties;
import com.example.news.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class NewConfig {
    @Bean
    public NewPersistencePort newPersistence() {
        return new NewJpaAdapter();
    }

    @Bean
    public NewServicePort newService() {
        return new NewServiceImpl(newPersistence());
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
