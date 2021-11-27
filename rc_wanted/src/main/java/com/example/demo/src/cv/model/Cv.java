package com.example.demo.src.cv.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cv {
    private int cvIdx;

    private String title;
    private String name;
    private String email;
    private String phone;
    private String introduction;
    private String updateDate;

    private String isMain;
    private String isUploaded;
    private String isCompleted;

    //for getCvList
    public Cv(int cvIdx, String title, String updateDate, String isMain, String isUploaded, String isCompleted) {
        this.cvIdx = cvIdx;
        this.title = title;
        this.updateDate = updateDate;
        this.isMain = isMain;
        this.isUploaded = isUploaded;
        this.isCompleted = isCompleted;
    }

    //for getCv
    @JsonCreator
    public Cv(String title, String name, String email, String phone, String introduction) {
        this.title = title;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.introduction = introduction;
    }
}
