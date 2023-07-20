package com.cvizard.pdfconverter.repository;

import com.cvizard.pdfconverter.model.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends MongoRepository<Resume, String> {

}