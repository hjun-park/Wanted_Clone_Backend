package com.example.demo.src.application.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetApplicationRes {
    private int userIdx;
    private String applicationStatus;
    private List<Application> applicationList;
}
