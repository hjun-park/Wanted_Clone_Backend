package com.example.demo.src.bookmark.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetBookmarksRes {

	private String image;
	private String title;
	private String companyName;
	private String country;
	private String region;
	private int reward;

	public GetBookmarksRes() {
	}

	public GetBookmarksRes(String image, String title, String companyName, String country, String region, int reward) {
		this.image = image;
		this.title = title;
		this.companyName = companyName;
		this.country = country;
		this.region = region;
		this.reward = reward;
	}
}
