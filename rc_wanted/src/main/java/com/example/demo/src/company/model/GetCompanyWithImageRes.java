package com.example.demo.src.company.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCompanyWithImageRes {

	private Long companyIdx;
	private Long countryIdx;
	private Long regionIdx;

	private String name;
	private String address;

	private String licenseNumber;

	private int sales;

	private int employees;

	private String intro;

	private String year;

	private String email;

	private String phone;

	private String url;

	private String keyword;

	private int responseRate;

	private String logo;
	private Long userIdx;

	private Double latitude;
	private Double longitude;

	private List<String> image;

	public GetCompanyWithImageRes() {
	}

	public GetCompanyWithImageRes(Long companyIdx, Long countryIdx, Long regionIdx, String name, String address, String licenseNumber, int sales, int employees, String intro, String year, String email, String phone, String url, String keyword, int responseRate, String logo, Long userIdx, Double latitude, Double longitude, List<String> image) {
		this.companyIdx = companyIdx;
		this.countryIdx = countryIdx;
		this.regionIdx = regionIdx;
		this.name = name;
		this.address = address;
		this.licenseNumber = licenseNumber;
		this.sales = sales;
		this.employees = employees;
		this.intro = intro;
		this.year = year;
		this.email = email;
		this.phone = phone;
		this.url = url;
		this.keyword = keyword;
		this.responseRate = responseRate;
		this.logo = logo;
		this.userIdx = userIdx;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
	}
}
