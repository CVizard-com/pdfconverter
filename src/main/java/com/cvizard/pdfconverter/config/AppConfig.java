package com.cvizard.pdfconverter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
//    @Bean
//    public String promptFromResource(String path) {
//        return ResourceReader.readFileToString("classpath:" + path);
//    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
