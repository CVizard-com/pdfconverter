package com.cvizard.pdfconverter.openai;

//import com.cvizard.pdfconverter.openai.Functions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatFunction;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAIAdapter {

    @Value("${settings.gpt-api-key}")
    private String token;
    private final ObjectMapper objectMapper;
    public static final Duration OPEN_AI_TIMEOUT = Duration.ofMinutes(2L);

    public String getGptResponse(List<ChatMessage> chatMessages) {
        final var openAiService = new OpenAiService(token, Duration.ofMinutes(2L));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-4")
                .messages(chatMessages)
                .build();
        log.info("This is your prompt {}", chatMessages);
        return openAiService.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage().getContent();
    }

    public Flowable<ChatCompletionChunk> getStreamResponse(List<ChatMessage> chatMessages) {
        final var openAiService = getOpenAiService();
        final var chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-4")
                .messages(chatMessages)
                .temperature(0.0)
                .build();

        log.info("This is your prompt {}", chatMessages);
        return openAiService.streamChatCompletion(chatCompletionRequest);
    }

    public OpenAiService getOpenAiService() {
//        final var client = defaultClient(token, Duration.ofMinutes(2));
//        final var retrofit = defaultRetrofit(client, objectMapper);
//
//        OpenAiApi api = retrofit.create(OpenAiApi.class);
//        return new OpenAiService(api);
        return new OpenAiService(token, OPEN_AI_TIMEOUT);
    }

    public <T> T getFunctionData(List<ChatMessage> chatMessages, Functions.Function<T> function) {
        final var openAiService = getOpenAiService();
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

    @Transactional
    public Flowable<ChatCompletionChunk> getGptResponse(UUID processId) {
        final var openAiService = new OpenAiService(token, Duration.ofMinutes(2L));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-4")
                .messages(List.of(
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), "You are an interviewer. Please generate 2 interview questions"),
                        new ChatMessage(ChatMessageRole.USER.value(), "I'm Java Developer.")
                ))
                .n(1)
                .build();

        return openAiService.streamChatCompletion(chatCompletionRequest);

    }

    public static class OpenAiException extends RuntimeException {
        public OpenAiException() {
        }

        public OpenAiException(Throwable cause) {
            super(cause);
        }
    }
}
