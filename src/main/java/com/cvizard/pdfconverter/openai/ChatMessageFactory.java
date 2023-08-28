package com.cvizard.pdfconverter.openai;

import com.cvizard.pdfconverter.config.AppConfig;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageFactory {
    private final AppConfig config;
    
    public List<ChatMessage> resumeChatMessages(String resumeText){

        return List.of(
                new ChatMessage(ChatMessageRole.USER.value(),
                        config.textFromResource("prompts/resumeChatMessages/user.txt") + resumeText),
                new ChatMessage(ChatMessageRole.SYSTEM.value(),
                        config.textFromResource("prompts/resumeChatMessages/system.txt"))
        );
    }

}
