package com.cvizard.pdfconverter.service;

import com.cvizard.pdfconverter.client.ChatGPTClient;
import com.cvizard.pdfconverter.config.AppConfig;
import com.cvizard.pdfconverter.config.ResourceReader;
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
import static com.cvizard.pdfconverter.model.ResumeStatus.READY;

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
//        String userPrompt = context.getBean( "promptFromResource","userPrompt.txt").toString();
//        String systemPrompt = context.getBean( "promptFromResource","systemPrompt.txt").toString();
//        String function = context.getBean("promptFromResource",  "function.txt").toString();
//        String functionCall = context.getBean( "promptFromResource","functionCall.txt").toString();

        String userPrompt = ResourceReader.readFileToString("classpath:userPrompt.txt").replace("[RESUME_TEXT]", resumeText);
        String systemPrompt = ResourceReader.readFileToString("classpath:systemPrompt.txt");
        String function = ResourceReader.readFileToString("classpath:function.txt");
        String functionCall = ResourceReader.readFileToString("classpath:functionCall.txt");



        ChatGPTRequest request = new ChatGPTRequest(model, userPrompt, systemPrompt, function, functionCall);
        ChatGPTResponse response = chatGPTClient.generateChatGPTResponse("Bearer " + apiKey, request);
        Resume resume = resumeParser(response
                .getChoices()
                .stream()
                .findFirst()
                .orElseThrow()
                .getMessage()
                .getFunction_call()
                .getArguments()
        );
        resume.setId(key);
        resume.setStatus(READY);
        resumeRepository.save(resume);
        System.out.println("saved " + key);
    }

    public Resume resumeParser(String resumeText) throws JsonProcessingException {
        return context.getBean(ObjectMapper.class).readValue(resumeText, Resume.class);
    }

}
