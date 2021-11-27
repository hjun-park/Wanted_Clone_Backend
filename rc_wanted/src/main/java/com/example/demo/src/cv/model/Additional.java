package com.example.demo.src.cv.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Additional {
    private int cvAdditionalIdx;

    private String date;
    private String title;
    private String content;
    private int displayOrder;


}
