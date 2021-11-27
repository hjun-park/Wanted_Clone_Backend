package com.example.demo.src.salary.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Salary {
    private int jobIdx;
    private String title;
    private int careerPeriod;
    private int avgSalary;

    public Salary(int jobIdx, int careerPeriod, int avgSalary) {
        this.jobIdx = jobIdx;
        this.careerPeriod = careerPeriod;
        this.avgSalary = avgSalary;
    }
}
