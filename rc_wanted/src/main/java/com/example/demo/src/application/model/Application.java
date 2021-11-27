package com.example.demo.src.application.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Getter
@Setter
@AllArgsConstructor
public class Application {
    private int applicationIdx;
    private int companyIdx;
    private String companyName;

    private int recruitIdx;
    private String recruitName;

    private String createdAt;
    private String applicationStatus;

    private String recommenderName;
    private boolean isCompensation;

    public Application(int applicationIdx, int companyIdx, int recruitIdx, String createdAt, String applicationStatus, String recommenderName, boolean isCompensation) {
        this.applicationIdx = applicationIdx;
        this.companyIdx = companyIdx;
        this.recruitIdx = recruitIdx;
        this.createdAt = createdAt;
        this.applicationStatus = applicationStatus;
        this.recommenderName = recommenderName;
        this.isCompensation = isCompensation;

    }



}
