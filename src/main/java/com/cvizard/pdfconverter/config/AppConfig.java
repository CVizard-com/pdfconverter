package com.cvizard.pdfconverter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class AppConfig {

    @Bean
    public String textFromResource(String path) {
        return ResourceReader.readFileToString("classpath:"+path);
    }
}
