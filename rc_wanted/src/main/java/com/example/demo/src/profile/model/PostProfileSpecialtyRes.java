package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostProfileSpecialtyRes {

	private int userIdx;
	private int userGroupIdx;
	private int jobResultNo;
	private int skillResultNo;
	private int profileLevel;

	public PostProfileSpecialtyRes(int userIdx, int userGroupIdx, int jobResultNo, int skillResultNo){
		this.userIdx = userIdx;
		this.userGroupIdx = userGroupIdx;
		this.jobResultNo = jobResultNo;
		this.skillResultNo = skillResultNo;
	}
}
