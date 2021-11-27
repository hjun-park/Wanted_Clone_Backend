package com.example.demo.src.recruit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostApplicationRes {
    private int userIdx;
    private int applicationIdx;
    private int cvResultNo;
}
