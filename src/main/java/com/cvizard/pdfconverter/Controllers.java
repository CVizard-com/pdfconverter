package com.cvizard.pdfconverter;

import com.cvizard.pdfconverter.model.Resume;
import com.cvizard.pdfconverter.repository.ResumeRepository;
import com.cvizard.pdfconverter.service.ResumeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class Controllers {

    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;


    @GetMapping("")
    public Resume testEndpoint(){
        String resumeText = "";
        String resumeKey = "1";
        try {
            resumeService.resumeConverter(resumeText, resumeKey);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Optional<Resume> resume;
        resume = resumeRepository.findById(resumeKey);
        return resume.get();
    }

}
