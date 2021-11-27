package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cv {
    private int userIdx;
    private String title;
    private String introduction;
    private String name;
    private String email;
    private String phone;
    private String startDate;
    private String endDate;
    private boolean isMain;

    public Cv(int userIdx, String name, String email, String phone){
        this.userIdx = userIdx;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isMain = true;
    }

    public boolean getIsMain(){
        return isMain;
    }

}
