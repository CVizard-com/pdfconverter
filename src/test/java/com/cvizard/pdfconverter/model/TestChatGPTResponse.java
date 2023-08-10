package com.cvizard.pdfconverter.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestChatGPTResponse {

    @Test
    public void testEmptyConstructor() {
        ChatGPTResponse response = new ChatGPTResponse();
        assertNull(response.getChoices());
        assertNull(response.getId());
        assertNull(response.getObject());
        assertNull(response.getUsage());
        assertNull(response.getModel());
        assertNull(response.getCreated());
    }

    @Test
    public void testAllArgsConstructor() {
        String id = "response-id";
        String object = "response-object";
        String model = "gpt-3.5";
        String created = "2023-08-10T12:34:56Z";
        ChatGPTResponse.Usage usage = new ChatGPTResponse.Usage();

        ChatGPTResponse response = new ChatGPTResponse(
                null, id, object, usage, model, created);

        assertNull(response.getChoices());
        assertEquals(id, response.getId());
        assertEquals(object, response.getObject());
        assertEquals(usage, response.getUsage());
        assertEquals(model, response.getModel());
        assertEquals(created, response.getCreated());
    }

    @Test
    public void testBuilder() {
        String id = "response-id";
        String object = "response-object";
        String model = "gpt-3.5";
        String created = "2023-08-10T12:34:56Z";
        ChatGPTResponse.Usage usage = new ChatGPTResponse.Usage();

        ChatGPTResponse response = ChatGPTResponse.builder()
                .id(id)
                .object(object)
                .usage(usage)
                .model(model)
                .created(created)
                .build();

        assertNull(response.getChoices());
        assertEquals(id, response.getId());
        assertEquals(object, response.getObject());
        assertEquals(usage, response.getUsage());
        assertEquals(model, response.getModel());
        assertEquals(created, response.getCreated());
    }

    @Test
    public void testNestedChoice() {
        int index = 1;
        ChatGPTMessage message = new ChatGPTMessage("assistant", "Here's your response.");
        String finishReason = "completed";

        ChatGPTResponse.Choice choice = new ChatGPTResponse.Choice();
        choice.setIndex(index);
        choice.setMessage(message);
        choice.setFinish_reason(finishReason);

        assertEquals(index, choice.getIndex());
        assertEquals(message, choice.getMessage());
        assertEquals(finishReason, choice.getFinish_reason());
    }

    @Test
    public void testNestedUsage() {
        String completionTokens = "10";
        String promptTokens = "5";
        String totalTokens = "15";

        ChatGPTResponse.Usage usage = new ChatGPTResponse.Usage();
        usage.setCompletion_tokens(completionTokens);
        usage.setPrompt_tokens(promptTokens);
        usage.setTotal_tokens(totalTokens);

        assertEquals(completionTokens, usage.getCompletion_tokens());
        assertEquals(promptTokens, usage.getPrompt_tokens());
        assertEquals(totalTokens, usage.getTotal_tokens());
    }

    @Test
    public void testEqualsAndHashCode() {
        ChatGPTResponse response1 = new ChatGPTResponse(null, "id1", "obj1", null, "model1", "created1");
        ChatGPTResponse response2 = new ChatGPTResponse(null, "id1", "obj1", null, "model1", "created1");
        ChatGPTResponse response3 = new ChatGPTResponse(null, "id2", "obj2", null, "model2", "created2");

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertNotEquals(response2, response3);

        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
}