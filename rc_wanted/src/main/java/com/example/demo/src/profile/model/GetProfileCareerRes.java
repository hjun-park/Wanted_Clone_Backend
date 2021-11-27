package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetProfileCareerRes {
    private int cvIdx;

    private String title;
    private String introduction;

    private String school;
    private String major;

    private String company;
    private String position;


}


