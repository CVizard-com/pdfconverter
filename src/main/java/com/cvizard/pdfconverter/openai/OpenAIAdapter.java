package com.cvizard.pdfconverter.openai;

import com.cvizard.pdfconverter.config.AppConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatFunction;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAIAdapter {

    @Value("${settings.gpt-api-key}")
    private String token;
    private final ObjectMapper objectMapper;
    private final AppConfig config;
   

    public <T> T getFunctionData(List<ChatMessage> chatMessages, Functions.Function<T> function) {
        final var openAiService = config.getOpenAiService(token);
        try {
            final var chatFunction = ChatFunction.builder()
                    .name(function.getName())
                    .description(function.getDescription())
                    .executor(function.getAClass(), interviewQuestions -> interviewQuestions)
                    .build();

            final var functionList = List.of(chatFunction);// list with functions
            final var functionExecutor = new FunctionExecutor(functionList);

            final var chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-4")
                    .messages(chatMessages)
                    .functions(functionExecutor.getFunctions())
                    .functionCall(ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(function.getName()))
                    .build();

            log.info("This is your prompt {}", chatMessages);
            final var responseMessage = openAiService.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
            final var functionCall = responseMessage.getFunctionCall();

            log.info("Response message for function [{}] is [{}]", function.getName(), responseMessage);

            ChatMessage functionResponseMessage = functionExecutor.executeAndConvertToMessageHandlingExceptions(functionCall);
            return objectMapper.readValue(functionResponseMessage.getContent(), function.getAClass());
        } catch (Exception e) {
            log.error("Exception while getting Function Data", e);
            throw new OpenAiException(e);
        }
    }

    public static class OpenAiException extends RuntimeException {
        public OpenAiException(Throwable cause) {
            super(cause);
        }
    }
}
