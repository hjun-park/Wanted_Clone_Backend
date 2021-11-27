package com.example.demo.src.cv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchCvStatusRes {
    private String activity;
    private boolean isSuccess;

    public PatchCvStatusRes(String activity, boolean isSuccess){
        this.activity = activity;
        this.isSuccess = isSuccess;
    }
}
