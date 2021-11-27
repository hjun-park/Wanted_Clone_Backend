package com.example.demo.src.cv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchCvStatusReq {
    private boolean status;
    private boolean isMain;

    public boolean getStatus(){
        return status;
    }
    public boolean getIsMain(){
        return isMain;
    }


}
