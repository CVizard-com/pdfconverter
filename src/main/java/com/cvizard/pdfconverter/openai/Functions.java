package com.cvizard.pdfconverter.openai;

import com.cvizard.pdfconverter.model.Resume;
import lombok.Value;

public interface Functions {
    String resumeFunctionName = "save_resume";
    String resumeFunctionDescription = "Save save_resume into db";

    Function<Resume> RESUME = Function.of(resumeFunctionName,
            resumeFunctionDescription,
            Resume.class);

    @Value(staticConstructor = "of")
    class Function<T> {
        String name;
        String description;
        Class<T> aClass;
    }
}
