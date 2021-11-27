package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostProfileRes {
    private String jwt;
    private int userIdx;
    private int profileLevel;

    public PostProfileRes(String jwt, int userIdx){
        this.jwt = jwt;
        this.userIdx = userIdx;
    }
}
