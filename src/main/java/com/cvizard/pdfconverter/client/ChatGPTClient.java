package com.cvizard.pdfconverter.client;

import com.cvizard.pdfconverter.model.ChatGPTRequest;
import com.cvizard.pdfconverter.model.ChatGPTResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "chatGPTClient", url = "${settings.gpt-endpoint}")
public interface ChatGPTClient {
    @PostMapping("/chat/completions")
    ChatGPTResponse generateChatGPTResponse(@RequestHeader("Authorization") String authorization,
                                            @RequestBody ChatGPTRequest request);
}