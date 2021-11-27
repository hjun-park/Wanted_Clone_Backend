package com.example.demo.src.cv.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCvRes {
    private int userIdx;
    private int cvIdx;

    private Cv cv;

    private List<Career> careerList;
    private List<Education> educationList;
    private List<Skill> skillList;
    private List<Additional> additionalList;
    private List<Language> languageList;
    private List<Link> linkList;





}
