package com.example.demo.src.cv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Education {
    private int cvEducationIdx;

    private String startDate;
    private String endDate;

    private String school;
    private String major;
    private String content;
    private int displayOrder;


}
