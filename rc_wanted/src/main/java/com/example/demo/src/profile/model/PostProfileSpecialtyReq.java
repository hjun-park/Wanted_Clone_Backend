package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostProfileSpecialtyReq {
    private int userIdx;
    private int jobGroupIdx;
    private int[] jobIdxes;
    private int[] skillIdxes;

    private int careerPeriod;
    private Salary salary;
}
