package com.example.demo.src.recruit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Valid
public class PostApplicationReq {

    private int userIdx;
    private int recruitIdx;
    private int companyIdx;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    private Integer recommenderIdx;
    private String recommenderName;

    private int[]  cvIdxes;

}
