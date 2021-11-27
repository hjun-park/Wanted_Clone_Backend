package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetProfileSpecialtyRes {

    private jobs jobGroup;
    private List<jobs> jobList;

    private int careerPeriod;

    private Salary salary;
    private List<Skill> skillList;
}


