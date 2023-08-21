package com.cvizard.pdfconverter.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequest {
    private String model;
    private List<ChatGPTMessage> messages;
    private int n;
    private double temperature;
    private List<String> functions;
    private String function_call;
    public ChatGPTRequest(String model, String userPrompt, String systemPrompt, String function, String function_call) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new ChatGPTMessage("system", systemPrompt));
        this.messages.add(new ChatGPTMessage("user", userPrompt));
        this.n =100;
        this.functions = new ArrayList<>();
        this.functions.add(function);
        this.function_call = function_call;
    }
}
