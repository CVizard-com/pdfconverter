package com.cvizard.pdfconverter.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestResume {

    @Test
    public void testCreateResume() {
        Resume resume = new Resume();
        assertNotNull(resume);
    }

    @Test
    public void testWorkClass() {
        Resume.Work work = new Resume.Work();
        work.setName("Company XYZ");
        work.setPosition("Software Engineer");

        assertEquals("Company XYZ", work.getName());
        assertEquals("Software Engineer", work.getPosition());
    }

    @Test
    public void testEducationClass() {
        Resume.Education education = new Resume.Education();
        education.setInstitution("University ABC");
        education.setArea("Computer Science");

        assertEquals("University ABC", education.getInstitution());
        assertEquals("Computer Science", education.getArea());
    }

    @Test
    public void testCertificateClass() {
        Resume.Certificate certificate = new Resume.Certificate();
        certificate.setName("Java Certification");
        certificate.setIssuer("Oracle");

        assertEquals("Java Certification", certificate.getName());
        assertEquals("Oracle", certificate.getIssuer());
    }

    @Test
    public void testSkillClass() {
        Resume.Skill skill = new Resume.Skill();
        skill.setName("Java");
        skill.setLevel("Advanced");

        assertEquals("Java", skill.getName());
        assertEquals("Advanced", skill.getLevel());
    }

    @Test
    public void testLanguageClass() {
        Resume.Language language = new Resume.Language();
        language.setLanguage("English");
        language.setLevel("Native");

        assertEquals("English", language.getLanguage());
        assertEquals("Native", language.getLevel());
    }

    @Test
    public void testProjectClass() {
        Resume.Project project = new Resume.Project();
        project.setName("Project ABC");
        project.setStartDate("2020-01-01");

        assertEquals("Project ABC", project.getName());
        assertEquals("2020-01-01", project.getStartDate());
    }

    @Test
    public void testInterestClass() {
        Resume.Interest interest = new Resume.Interest();
        interest.setName("Reading");
        interest.setKeywords(List.of(new String[]{"Books", "Literature", "Fiction", "Non-Fiction", "Drama"}));

        assertEquals("Reading", interest.getName());
        assertEquals(List.of(new String[]{"Books", "Literature", "Fiction", "Non-Fiction", "Drama"}), interest.getKeywords());
    }


    @Test
    public void testToString() {
        Resume resume = new Resume();
        resume.setId("12345");
        assertNotNull(resume.toString());
    }

    @Test
    public void testWorkGetters() {
        Resume.Work work = new Resume.Work();
        work.setName("Company XYZ");
        work.setPosition("Software Engineer");

        assertEquals("Company XYZ", work.getName());
        assertEquals("Software Engineer", work.getPosition());
    }

    @Test
    public void testEducationGetters() {
        Resume.Education education = new Resume.Education();
        education.setInstitution("University ABC");
        education.setArea("Computer Science");

        assertEquals("University ABC", education.getInstitution());
        assertEquals("Computer Science", education.getArea());
    }

    @Test
    public void testCertificateGetters() {
        Resume.Certificate certificate = new Resume.Certificate();
        certificate.setName("Java Certification");
        certificate.setIssuer("Oracle");

        assertEquals("Java Certification", certificate.getName());
        assertEquals("Oracle", certificate.getIssuer());
    }
    @Test
    public void testSkillGetters() {
        Resume.Skill skill = new Resume.Skill();
        skill.setName("Java");
        skill.setLevel("Advanced");

        assertEquals("Java", skill.getName());
        assertEquals("Advanced", skill.getLevel());
    }

    @Test
    public void testLanguageGetters() {
        Resume.Language language = new Resume.Language();
        language.setLanguage("English");
        language.setLevel("Native");

        assertEquals("English", language.getLanguage());
        assertEquals("Native", language.getLevel());
    }

    @Test
    public void testProjectGetters() {
        Resume.Project project = new Resume.Project();
        project.setName("Project ABC");
        project.setStartDate("2020-01-01");

        assertEquals("Project ABC", project.getName());
        assertEquals("2020-01-01", project.getStartDate());
    }

    @Test
    public void testInterestGetters() {
        Resume.Interest interest = new Resume.Interest();
        interest.setName("Reading");
        interest.setKeywords(List.of(new String[]{"Books", "Literature", "Fiction", "Non-Fiction", "Drama"}));

        assertEquals("Reading", interest.getName());
        assertEquals(List.of(new String[]{"Books", "Literature", "Fiction", "Non-Fiction", "Drama"}), interest.getKeywords());
    }

}
