package com.cvizard.pdfconverter.repositories;

import com.cvizard.pdfconverter.models.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends MongoRepository<Resume, String> {

}