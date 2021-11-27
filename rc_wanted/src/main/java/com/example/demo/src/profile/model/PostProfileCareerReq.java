package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostProfileCareerReq {
    private int userIdx;
    private String school;
    private String company;
    private String startDate;
    private String endDate;
}
