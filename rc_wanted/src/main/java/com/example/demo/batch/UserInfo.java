package com.example.demo.batch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;
    private Long jobIdx;
    private String salaryPeriod;
    private int salary;
    private int totalSalary;
    private int careerPeriod;

    public UserInfo(Long userIdx, Long jobIdx, String salaryPeriod, int salary, int careerPeriod){
        this.userIdx = userIdx;
        this.jobIdx = jobIdx;
        this.salaryPeriod = salaryPeriod;
        this.salary = salary;
        this.careerPeriod = careerPeriod;
    }




}