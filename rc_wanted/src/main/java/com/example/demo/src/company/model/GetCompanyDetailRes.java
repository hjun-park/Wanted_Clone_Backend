package com.example.demo.src.company.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCompanyDetailRes {

	private String logo;
	private String companyName;
	private int responseRate;
	private List<String> companyImages;
	private String intro;
	private List<String> tagNames;

	public GetCompanyDetailRes() {
	}

	public GetCompanyDetailRes(String logo, String companyName, int responseRate, List<String> companyImages, String intro, List<String> tagNames) {
		this.logo = logo;
		this.companyName = companyName;
		this.responseRate = responseRate;
		this.companyImages = companyImages;
		this.intro = intro;
		this.tagNames = tagNames;
	}

}
