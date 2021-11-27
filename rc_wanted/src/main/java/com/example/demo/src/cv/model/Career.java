package com.example.demo.src.cv.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Career {
    private int cvCareerIdx;

    private String startDate;
    private String endDate;

    private String company;
    private String position;
    private int displayOrder;

    private List<Accomp> accompList;

    public Career(int cvCareerIdx, String startDate, String endDate, String company, String position, int displayOrder){
        this.cvCareerIdx = cvCareerIdx;
        this.startDate = startDate;
        this.endDate = endDate;
        this.company = company;
        this.position = position;
        this.displayOrder = displayOrder;
    }
}
