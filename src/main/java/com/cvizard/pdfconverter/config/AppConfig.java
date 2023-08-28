package com.cvizard.pdfconverter.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.time.Duration;

@Configuration
@Lazy
public class AppConfig {

    @Bean
    public String textFromResource(String path) {
        return ResourceReader.readFileToString("classpath:"+path);
    }

    @Bean
    public OpenAiService getOpenAiService(String token) {
        return new OpenAiService(token, Duration.ofMinutes(2L));
    }
}
