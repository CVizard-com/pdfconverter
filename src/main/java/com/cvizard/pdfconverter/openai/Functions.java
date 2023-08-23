package com.cvizard.pdfconverter.openai;

import com.cvizard.pdfconverter.model.Resume;
import lombok.Value;

public interface Functions {

    Function<Resume> RESUME = Function.of("save_resume",
            "Save save_resume into db",
            Resume.class);

    @Value(staticConstructor = "of")
    class Function<T> {
        String name;
        String description;
        Class<T> aClass;
    }
}
