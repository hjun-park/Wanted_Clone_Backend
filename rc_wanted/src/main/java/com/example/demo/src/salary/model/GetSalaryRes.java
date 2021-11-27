package com.example.demo.src.salary.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetSalaryRes {
    private String type;
    private int jobIdx;
    private String title;
    private String period;
    private int avgSalary;

    public GetSalaryRes(String type, int jobIdx){
        this.type = type;
        this.jobIdx = jobIdx;
    }
}
