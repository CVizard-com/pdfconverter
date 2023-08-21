package com.cvizard.pdfconverter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTMessage {
    private String role;
    private String content;
    private FunctionCall function_call;
    
    public ChatGPTMessage(String role, String content) {
        this.role = role;
        this.content = content;
        this.function_call = null;
    }
}
