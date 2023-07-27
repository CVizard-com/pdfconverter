package com.cvizard.pdfconverter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@NoArgsConstructor
@Data
@Document("cvizard")
public class Resume {
    @MongoId
    private String id;
    private List<Work> work;
    private List<Education> education;
    private List<Certificate> certificates;
    private List<Skill> skills;
    private List<Language> languages;
    private List<Project> projects;
    private List<Interest> interests;
    
    @NoArgsConstructor
    @Data
    public static class Work{
        private String name;
        private String position;
        private String startDate;
        private String endDate;
        private String summary;
    }
    
    @NoArgsConstructor
    @Data
    public static class Education{
        private String institution;
        private String area;
        private String studyType;
        private String startDate;
        private String endDate;
    }
    
    @NoArgsConstructor
    @Data
    public static class Certificate{
        private String name;
        private String date;
        private String issuer;
    }
    
    @NoArgsConstructor
    @Data
    public static class Skill{
        private String name;
        private String level;
        private List<String> keywords;
    }
    
    @NoArgsConstructor
    @Data
    public static class Language{
        private String language;
        private String level;
    }
    
    @NoArgsConstructor
    @Data
    public static class Project{
        private String name;
        private String startDate;
        private String endDate;
        private String summary;
    }
    
    @NoArgsConstructor
    @Data
    public static class Interest{
        private String name;
        private List<String> keywords;
    }


}

