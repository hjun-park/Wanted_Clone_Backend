package com.example.demo.src.recruit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetRecruitsRes {

	private Long recruitIdx;
	private Long companyIdx;
	private String image;
	private String recruitTitle;
	private String companyName;
	private String country;
	private String region;
	private int reward;
	private int likeCount;
	private int responseRate;
	private int minExperience;

	public GetRecruitsRes(Long recruitIdx, Long companyIdx, String image, String recruitTitle, String companyName, String country, String region, int reward, int likeCount, int responseRate, int minExperience) {
		this.recruitIdx = recruitIdx;
		this.companyIdx = companyIdx;
		this.image = image;
		this.recruitTitle = recruitTitle;
		this.companyName = companyName;
		this.country = country;
		this.region = region;
		this.reward = reward;
		this.likeCount = likeCount;
		this.responseRate = responseRate;
		this.minExperience = minExperience;
	}

	private GetRecruitsRes() {
	}
}
