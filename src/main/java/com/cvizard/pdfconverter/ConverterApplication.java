package com.cvizard.pdfconverter;

import com.cvizard.pdfconverter.repository.ResumeRepository;
import com.cvizard.pdfconverter.service.ResumeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableFeignClients
@EnableMongoRepositories(basePackageClasses = ResumeRepository.class)
public class ConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConverterApplication.class, args);
    }

}
