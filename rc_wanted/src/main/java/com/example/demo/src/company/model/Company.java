package com.example.demo.src.company.model;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

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


	@Builder
	public Company(Long companyIdx, Long countryIdx, Long regionIdx, String name, String address, String licenseNumber, int sales, int employees, String intro, String year, String email, String phone, String url, String keyword, int responseRate, String logo, Long userIdx, Double latitude, Double longitude) {
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
	}
}
