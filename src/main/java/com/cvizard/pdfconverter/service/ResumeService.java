package com.cvizard.pdfconverter.service;

import com.cvizard.pdfconverter.client.ChatGPTClient;
import com.cvizard.pdfconverter.config.AppConfig;
import com.cvizard.pdfconverter.model.ChatGPTRequest;
import com.cvizard.pdfconverter.model.ChatGPTResponse;
import com.cvizard.pdfconverter.model.Resume;
import com.cvizard.pdfconverter.repository.ResumeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static com.cvizard.pdfconverter.model.ResumeStatus.PROCESSING;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final ChatGPTClient chatGPTClient;
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @Value("${settings.gpt-api-key}")
    private String apiKey;
    @Value("${settings.model}")
    private String model;

    @Bean
    public Consumer<Message<String>> myConsumer() {
        return message -> {
            String payload = message.getPayload();
            byte[] keyBytes = message.getHeaders().get(KafkaHeaders.RECEIVED_KEY, byte[].class);
            String key = new String(keyBytes, StandardCharsets.UTF_8);
            System.out.println("payload = " + payload + " key = " + key);
            try {
                resumeConverter(payload, key);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public void resumeConverter(String resumeText, String key) throws JsonProcessingException {
        resumeRepository.save(Resume.builder().id(key).status(PROCESSING).build());
        String prompt = context.getBean("promptFromResource", String.class)
                .replace("[RESUME TEXT]", resumeText);
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse response = chatGPTClient.generateChatGPTResponse("Bearer " + apiKey, request);
        Resume resume = resumeParser(response
                .getChoices()
                .stream()
                .findFirst()
                .orElseThrow()
                .getMessage()
                .getContent());
        resume.setId(key);
        resumeRepository.save(resume);
        System.out.println("saved " + key);
    }

    public Resume resumeParser(String resumeText) throws JsonProcessingException {
        return context.getBean(ObjectMapper.class).readValue(resumeText, Resume.class);
    }
}
