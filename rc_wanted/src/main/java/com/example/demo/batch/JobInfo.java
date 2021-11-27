package com.example.demo.batch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class JobInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobSalaryIdx;
    private Long jobIdx;
    private int count;
    private int totalSalary;
    private int avgSalary;

    public JobInfo(Long jobIdx, int count, int totalSalary){
        this.jobIdx = jobIdx;
        this.count = count;
        this.totalSalary = totalSalary;
    }

    public JobInfo(Long jobSalaryIdx, Long jobIdx, int count, int totalSalary){
        this.jobSalaryIdx = jobSalaryIdx;
        this.jobIdx = jobIdx;
        this.count = count;
        this.totalSalary = totalSalary;
    }

}