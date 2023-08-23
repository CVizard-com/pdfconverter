package com.cvizard.pdfconverter.service;

import com.cvizard.pdfconverter.config.AppConfig;
import com.cvizard.pdfconverter.model.Functions;
import com.cvizard.pdfconverter.model.Resume;
import com.cvizard.pdfconverter.openai.ChatMessageFactory;
import com.cvizard.pdfconverter.openai.OpenAIAdapter;
import com.cvizard.pdfconverter.repository.ResumeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
    private final OpenAIAdapter openAIAdapter;
    private final ChatMessageFactory messageFactory;

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

        Resume resume = openAIAdapter.getFunctionData(
                messageFactory.resumeChatMessages(resumeText),
                Functions.RESUME);

        resume.setId(key);
        resume.setStatus(READY);
        resumeRepository.save(resume);
        System.out.println("saved " + key);
    }
}
