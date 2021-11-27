package com.example.demo.src.cv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Link {

    private int cvLinkIdx;
    private String link;
    private int displayOrder;

}
