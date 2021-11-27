package com.example.demo.src.cv.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LanguageTest {
    private int cvLanguageIdx;
    private int cvLanguageTestIDx;

    private String date;

    private String title;
    private String score;
    private int displayOrder;

    public LanguageTest(int cvLanguageTestIdx, String title, String score, String date, int displayOrder) {
        this.cvLanguageIdx = cvLanguageTestIdx;
        this.title = title;
        this.score = score;
        this.date = date;
        this.displayOrder = displayOrder;
    }
}
