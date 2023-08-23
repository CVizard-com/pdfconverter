package com.cvizard.pdfconverter.openai;

import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageFactory {
    public List<ChatMessage> resumeChatMessages(String resumeText){

        return List.of(
                new ChatMessage(ChatMessageRole.USER.value(), "Scraped raw resume content is: " + resumeText),
                new ChatMessage(ChatMessageRole.SYSTEM.value(), "Use the provided scraped resume text to call a function save_resume. " +
                        "                                      Don't make assumptions about what values to plug into functions." +
                        "                                      If there is no info about given field, leave it empty.")
        );
    }

}
