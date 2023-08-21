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
    private String id;
    private String object;
    private Usage usage;
    private String model;
    private String created;

    @Data
    public static class Choice {
        private int index;
        private ChatGPTMessageFunction message;
        private String finish_reason;
    }

    @Data
    public static class Usage{
        private String completion_tokens;
        private String prompt_tokens;
        private String total_tokens;
    }
}