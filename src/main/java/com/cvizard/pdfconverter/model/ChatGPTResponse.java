package com.cvizard.pdfconverter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private int index;
        private ChatGPTMessage message;
    }
}