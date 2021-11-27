package com.example.demo.src.profile.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserRes {
    private int userIdx;
    private String profileImage;
    private String name;
    private String email;
    private String phone;
    private String purpose;
    private String interest;

    public GetUserRes(int userIdx, String profileImage, String name, String email, String phone, String purpose){
        this.userIdx = userIdx;
        this.profileImage = profileImage;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.purpose = purpose;
    }
}
