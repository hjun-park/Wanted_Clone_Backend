package com.example.demo.src.recruit.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class GetRecruitsCompany {

	private String title;
	private int reward;
	private Boolean isAlways;
	private Date deadline;

	private GetRecruitsCompany() {
	}

	public GetRecruitsCompany(String title, int reward, Boolean isAlways, Date deadline) {
		this.title = title;
		this.reward = reward;
		this.isAlways = isAlways;
		this.deadline = deadline;
	}
}
