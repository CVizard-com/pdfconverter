package com.cvizard.pdfconverter.controller;

import com.cvizard.pdfconverter.openai.Functions;
import com.cvizard.pdfconverter.model.Resume;
import com.cvizard.pdfconverter.openai.ChatMessageFactory;
import com.cvizard.pdfconverter.openai.OpenAIAdapter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class Test {
    private final ChatMessageFactory chatMessageFactory;
    private final OpenAIAdapter openAIAdapter;
    @GetMapping
    public Resume getResponse(){
        return openAIAdapter.getFunctionData(
                chatMessageFactory.resumeChatMessages("johnnie.ramos@gmail.com 708-678-627 Warsaw, Poland,\n" +
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
                        " SQL git"),
                Functions.RESUME);

    }
}
