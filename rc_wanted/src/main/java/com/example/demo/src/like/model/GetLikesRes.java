package com.example.demo.src.like.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetLikesRes {

	private String image;
	private String title;
	private String companyName;
	private String country;
	private int reward;
	private String region;

	public GetLikesRes() {
	}

	public GetLikesRes(String image, String title, String companyName, String country, int reward, String region) {
		this.image = image;
		this.title = title;
		this.companyName = companyName;
		this.country = country;
		this.reward = reward;
		this.region = region;
	}
}
