package com.cvizard.pdfconverter.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestChatGPTMessage{

    @Test
    public void testEmptyConstructor() {
        ChatGPTMessage message = new ChatGPTMessage();
        assertNull(message.getRole());
        assertNull(message.getContent());
    }

    @Test
    public void testAllArgsConstructor() {
        String role = "user";
        String content = "Hello, ChatGPT!";
        ChatGPTMessage message = new ChatGPTMessage(role, content);

        assertEquals(role, message.getRole());
        assertEquals(content, message.getContent());
    }

    @Test
    public void testBuilder() {
        String role = "assistant";
        String content = "Sure, I can help!";
        ChatGPTMessage message = ChatGPTMessage.builder()
                .role(role)
                .content(content)
                .build();

        assertEquals(role, message.getRole());
        assertEquals(content, message.getContent());
    }

    @Test
    public void testSetterMethods() {
        ChatGPTMessage message = new ChatGPTMessage();
        String role = "user";
        String content = "This is a test message.";

        message.setRole(role);
        message.setContent(content);

        assertEquals(role, message.getRole());
        assertEquals(content, message.getContent());
    }

    @Test
    public void testEqualsAndHashCode() {
        ChatGPTMessage message1 = new ChatGPTMessage("user", "Hello");
        ChatGPTMessage message2 = new ChatGPTMessage("user", "Hello");
        ChatGPTMessage message3 = new ChatGPTMessage("assistant", "Hi there");

        assertEquals(message1, message2);
        assertNotEquals(message1, message3);
        assertNotEquals(message2, message3);

        assertEquals(message1.hashCode(), message2.hashCode());
        assertNotEquals(message1.hashCode(), message3.hashCode());
    }
}
