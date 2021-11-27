package com.example.demo.src.cv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchCvReq {
    private int cvIdx;
    private int userIdx;

    private Cv cv;

    private List<Career> careerList;
    private List<Education> educationList;
    private List<Skill> skillList;
    private List<Additional> additionalList;
    private List<Language> languageList;
    private List<Link> linkList;



}
