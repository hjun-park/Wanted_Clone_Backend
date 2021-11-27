package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostProfileInterestReq {
    private int userIdx;
    private String purpose;
    private int[] interestIdxes;
}
