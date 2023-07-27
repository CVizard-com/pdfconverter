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
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

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
    public Consumer<Message<String>> myConsumer(){
        return message -> {
            String payload = message.getPayload();
            MessageHeaders headers = message.getHeaders();
            Object key = headers.get(KafkaHeaders.RECEIVED_KEY);
            System.out.println("payload = " + payload + " key = " + key);
            try {
                resumeConverter(payload, key.toString());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

     public void resumeConverter(String resumeText, String key) throws JsonProcessingException {
        String prompt = context.getBean("promptFromResource", String.class)
                .replace("[RESUME TEXT]",resumeText);
        ChatGPTRequest request = new ChatGPTRequest(model,prompt);
        ChatGPTResponse response = chatGPTClient.generateChatGPTResponse("Bearer "+apiKey,request);
        Resume resume = resumeParser(response
                .getChoices()
                .stream()
                .findFirst()
                .orElseThrow()
                .getMessage()
                .getContent());
        resume.setId(key);
         resumeRepository.insert(resume);
         System.out.println("saved "+ key);
     }

     public Resume resumeParser(String resumeText) throws JsonProcessingException {
         return context.getBean(ObjectMapper.class).readValue(resumeText, Resume.class);
     }
}
