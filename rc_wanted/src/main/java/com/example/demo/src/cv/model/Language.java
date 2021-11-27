package com.example.demo.src.cv.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Language {
    private int cvLanguageIdx;

    private String category;
    private String level;
    private int displayOrder;

    private List<LanguageTest> languageTestList;

    public Language(int cvLanguageIdx, String category, String level, int displayOrder) {
        this.cvLanguageIdx = cvLanguageIdx;
        this.category = category;
        this.level = level;
        this.displayOrder = displayOrder;
    }
}
