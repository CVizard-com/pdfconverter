package com.cvizard.pdfconverter.model;


import org.junit.jupiter.api.Test;
        import java.util.List;
        import static org.junit.jupiter.api.Assertions.*;

public class TestChatGPTRequestModel {


    @Test
    public void testConstructor() {
        String model = "gpt-3.5";
        String prompt = "Hello, ChatGPT!";
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);

        assertEquals(model, request.getModel());

        List<ChatGPTMessage> messages = request.getMessages();
        assertNotNull(messages);
        assertEquals(1, messages.size());

        ChatGPTMessage message = messages.get(0);
        assertEquals("user", message.getRole());
        assertEquals(prompt, message.getContent());

        assertEquals(100, request.getN());
        assertEquals(0.0, request.getTemperature(), 0.0001); // Default value
    }

    @Test
    public void testSettersAndGetters() {
        ChatGPTRequest request = new ChatGPTRequest("gpt-2.0", "Testing.");

        request.setModel("gpt-4.0");
        assertEquals("gpt-4.0", request.getModel());

        request.setN(50);
        assertEquals(50, request.getN());

        request.setTemperature(0.8);
        assertEquals(0.8, request.getTemperature(), 0.0001);
    }

    @Test
    public void testEqualsAndHashCode() {
        ChatGPTRequest request1 = new ChatGPTRequest("gpt-3.5", "Hello");
        ChatGPTRequest request2 = new ChatGPTRequest("gpt-3.5", "Hello");
        ChatGPTRequest request3 = new ChatGPTRequest("gpt-4.0", "Hi");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);

        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }
}
