package com.cvizard.pdfconverter.service;

import com.cvizard.pdfconverter.model.Resume;
import com.cvizard.pdfconverter.repository.ResumeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
    @AutoConfigureMockMvc
    public class TestResumeService {

    @MockBean
    private ResumeService resumeService;

    @MockBean
    private ResumeRepository resumeRepository;

    @Autowired
    private MockMvc mockMvc;

    public String createResumeText() {
        String profile = "Ambitious person, willing to grind in order to make it on top";

        String certificates = "CISCO CCNA ROUTING AND SWITCHING";

        String skills = "HTML\nCSS";

        String education = "Software Engineering,\nEmory University\nJanuary 2014 – March 2025\n|\nAtlanta, United States";

        String professionalExperience = "Streets, Drug Dealer\nJanuary 2006 – August 2010\n|\nAtlanta, United States";

        String projects = "Drug dealing Tor website\nJanuary 2021 – September 2024";

        String languages = "Polish (C2)\nGerman (B2)";

        String contactInfo = "Lucas Smith\nFrontend Developer\nkurwa@roksa.sx\n432012578\nAtlanta, United States of America\nPolish\nhttps://linkedin.com/popekmonster\nhttps://github.com/dupa\nB- Category";

        String resumeText =
                "Profile:\n" + profile + "\n\n" +
                        "Certificates:\n" + certificates + "\n\n" +
                        "Skills:\n" + skills + "\n\n" +
                        "Education:\n" + education + "\n\n" +
                        "Professional Experience:\n" + professionalExperience + "\n\n" +
                        "Projects:\n" + projects + "\n\n" +
                        "Languages:\n" + languages + "\n\n" +
                        "Contact Information:\n" + contactInfo;
        return resumeText;
    }

    @Test
    public void testResumeConverter() throws JsonProcessingException {
        // Arrange
        String resumeText = createResumeText();
        String key = "1234";

        // Act
        resumeService.resumeConverter(resumeText, key);

        // Assert
        Resume resumeEntity = resumeRepository.findById(key).orElse(null);
        assertThat(resumeEntity).isNotNull();

        assertThat(resumeEntity.getCertificates())
                .as("Certificates should match")
                .isEqualTo("CISCO CCNA ROUTING AND SWITCHING");

        assertThat(resumeEntity.getSkills())
                .as("Skills should match")
                .isEqualTo("HTML\nCSS");

        assertThat(resumeEntity.getEducation())
                .as("Education should match")
                .isEqualTo("Software Engineering,\nEmory University\nJanuary 2014 – March 2025\n|\nAtlanta, United States");

        assertThat(resumeEntity.getProjects())
                .as("Projects should match")
                .isEqualTo("Drug dealing Tor website\nJanuary 2021 – September 2024");

        assertThat(resumeEntity.getLanguages())
                .as("Languages should match")
                .isEqualTo("Polish (C2)\nGerman (B2)");
    }

}
