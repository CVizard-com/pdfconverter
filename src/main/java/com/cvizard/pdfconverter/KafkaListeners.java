package com.cvizard.pdfconverter;

import com.cvizard.pdfconverter.services.ResumeService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaListeners {
    private final ResumeService resumeService;
//    @KafkaListener(topics = "${settings.kafka.topics.cleaned-text}", groupId = "ooo")
    @KafkaListener(topics = "cleaned-text", groupId = "ooo")
    void listener(@Payload String data, @Header(KafkaHeaders.RECEIVED_KEY) String key){
        resumeService.resumeConverter(data, key);
    }
}
