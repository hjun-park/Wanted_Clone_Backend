package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PutUserReq {
    private int userIdx;
    private String name;
    private String email;
    private String phoneCountryCode;
    private String phone;
}
