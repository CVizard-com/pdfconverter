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

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new ChatGPTMessage("user", prompt));
        this.n =100;
    }
}
