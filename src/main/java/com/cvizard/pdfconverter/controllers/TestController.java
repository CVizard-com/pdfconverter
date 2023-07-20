package com.cvizard.pdfconverter.controllers;

import com.cvizard.pdfconverter.ChatGPTRequest;
import com.cvizard.pdfconverter.ChatGPTResponse;
import com.cvizard.pdfconverter.Message;
import com.cvizard.pdfconverter.Test;
import com.cvizard.pdfconverter.interfaces.ChatGPTClient;
import com.cvizard.pdfconverter.models.Resume;
import com.cvizard.pdfconverter.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final ChatGPTClient chatGPTClient;
    private final KafkaTemplate<String,String> template;
    private final ResumeService resumeService;
    @Value(value = "${settings.gpt-api-key}")
    private String apiKey;
    @PostMapping("gpt")
    public String testGPTPrompt(@RequestBody Test test){
        ChatGPTRequest request = new ChatGPTRequest(test.getModel(),test.getPrompt());
        ChatGPTResponse response = chatGPTClient.generateChatGPTResponse("Bearer "+apiKey,request);
        return response.getChoices().get(0).getMessage().getContent();
    }
    @PostMapping("topic")
    public void postToKafka(@RequestBody Message message){
        template.send("cleaned-text",message.getContent());
    }
    @GetMapping("")
    public Resume resumeTest(){
        return resumeService.resumeConverter("****", UUID.randomUUID().toString());
    }
}