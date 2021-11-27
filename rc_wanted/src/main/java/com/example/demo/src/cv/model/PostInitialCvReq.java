package com.example.demo.src.cv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostInitialCvReq {
    private int userIdx;

    private String title;
    private String introduction;

    private String name;
    private String email;
    private String phone;

    public PostInitialCvReq(int userIdx, String name, String email, String phone){
        this.userIdx = userIdx;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


}
