package com.cvizard.pdfconverter.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatGPTMessage {
    private String role;
    private String content;
}
