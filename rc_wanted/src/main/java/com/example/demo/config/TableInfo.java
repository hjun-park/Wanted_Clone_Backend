package com.example.demo.config;

import lombok.Getter;

@Getter
public enum TableInfo {
    CvAdditional("CvAdditional", "cvAdditionalIdx", "cvIdx"),
    CvCareer("CvCareer", "cvCareerIdx", "cvIdx"),
    CvCareerAccomp("CvCareerAccomp", "cvCareerAccompIdx", "cvCareerIdx"),
    CvEducation("CvEducation", "cvEducationIdx", "cvIdx"),
    CvLanguage("CvLanguage", "cvLanguageIdx", "cvIdx"),
    CvLanguageTest("CvLanguageTest", "cvLanguageTestIdx", "cvLanguageIdx"),
    CvLink("CvLink", "cvLinkIdx", "cvIdx");

    final private String name;
    final private String primarKey;
    final private String parentKey;

    private TableInfo(String name, String primaryKey, String parentKey){
        this.name = name;
        this.primarKey = primaryKey;
        this.parentKey = parentKey;
    }
    public String getName(){
        return name;
    }
}
