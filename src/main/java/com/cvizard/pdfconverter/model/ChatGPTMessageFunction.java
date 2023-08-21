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
public class ChatGPTMessageFunction {
    private String role;
    private String content;
    private FunctionCall function_call;
}
