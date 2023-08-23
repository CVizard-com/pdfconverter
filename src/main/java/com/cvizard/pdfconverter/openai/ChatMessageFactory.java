package com.cvizard.pdfconverter.openai;

import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageFactory {
    public List<ChatMessage> resumeChatMessages(String resumeText){
        final var scrapedPdf = " Johnnie Ramos\n" +
                "johnnie.ramos@gmail.com 708-678-627 Warsaw, Poland,\n" +
                " Education\n" +
                "2015/10 – 2020/05 London, UK\n" +
                "Languages Polish\n" +
                "C2\n" +
                "A-Level Degree\n" +
                "Abbey DLD College London\n" +
                "Spanish\n" +
                "B1\n" +
                "  Certificates\n" +
                "Certified Customer Service Professional (CCSP)\n" +
                "2016/10\n" +
                "Professional Experience\n" +
                "  2020/01 – present 2019/08 – 2019/12\n" +
                "Projects\n" +
                "2022/01 – 2022/11\n" +
                "Skills\n" +
                "Spring Boot Docker python\n" +
                "IT Supervisor\n" +
                "NextGen Information\n" +
                "Research, identify and appraise emerging technologies, hardware, and software to provide strategic recommendations for continuous improvements\n" +
                "IT Specialist\n" +
                "INITAR Inc.\n" +
                "Oversaw more than 200 computers of the company by monitoring, configuring, and maintaining all hardware and software systems\n" +
                " Online store\n" +
                "online store for clothes shopping\n" +
                " SQL git\n";
        return List.of(
                new ChatMessage(ChatMessageRole.USER.value(), "Scraped raw resume content is: " + scrapedPdf),
                new ChatMessage(ChatMessageRole.SYSTEM.value(), "Use the provided scraped resume text to call a function save_resume. " +
                        "                                      Don't make assumptions about what values to plug into functions." +
                        "                                      If there is no info about given field, leave it empty.")
        );
    }

}
