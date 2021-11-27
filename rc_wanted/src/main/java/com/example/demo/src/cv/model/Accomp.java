package com.example.demo.src.cv.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Accomp {
    private int cvCareerIdx;
    private int cvCareerAccompIdx;

    private String startDate;
    private String endDate;

    private String title;
    private String intro;
    private int displayOrder;



    public Accomp(int cvCareerIdx, int cvCareerAccompIdx, String startDate, String endDate, String title, String intro, int displayOrder){
        this.cvCareerIdx = cvCareerIdx;
        this.cvCareerAccompIdx = cvCareerAccompIdx;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.intro = intro;
        this.displayOrder = displayOrder;
    }


}
