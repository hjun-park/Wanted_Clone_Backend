package com.example.demo.src.recruit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetRecruitDetailRes {

	private Long recruitIdx;
	private List<String> imageList;
	private String recruitTitle;
	private String recruitContent;
	private int responseRate;
	private String region;
	private String country;
	private List<String> tags;
	private String companyName;
	private Date deadline;
	private String address;
	private int reward;
	private String companyLogo;
	private Double latitude;
	private Double longitude;

	private GetRecruitDetailRes() {
	}

	public GetRecruitDetailRes(Long recruitIdx, List<String> imageList, String recruitTitle, String recruitContent, int responseRate, String region, String country, List<String> tags, String companyName, Date deadline, String address, int reward, String companyLogo, Double latitude, Double longitude) {
		this.recruitIdx = recruitIdx;
		this.imageList = imageList;
		this.recruitTitle = recruitTitle;
		this.recruitContent = recruitContent;
		this.responseRate = responseRate;
		this.region = region;
		this.country = country;
		this.tags = tags;
		this.companyName = companyName;
		this.deadline = deadline;
		this.address = address;
		this.reward = reward;
		this.companyLogo = companyLogo;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
