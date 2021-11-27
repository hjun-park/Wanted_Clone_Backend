package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Salary {
    private String salaryPeriod;
    private String salaryCurrency;
    private int salaryValue;
}
