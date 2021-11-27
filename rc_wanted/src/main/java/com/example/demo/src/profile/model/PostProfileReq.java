package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostProfileReq {
    private String email;
    private String name;
    private String phoneCountryCode;
    private String phone;
    private String password;
    private String passwordConfig;
    private boolean isAcceptEmail;

    public boolean getIsAcceptEmail(){
        return isAcceptEmail;
    }

    public void setIsAcceptEmail(boolean isAcceptEmail){
        this.isAcceptEmail = isAcceptEmail;
    }
}
