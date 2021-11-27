package com.example.demo.src.profile.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDTO {

	private Long userIdx;
	private String email;
	private String name;
	private String password;
	private Boolean isAcceptEmail;
	private String phone;
	private int salary;
	private String salaryPeriod;
	private String salaryCurrency;
	private int careerPeriod;
	private String searchStatus;
	private Long recommenderIdx;
	private String profileImage;
	private int profileLevel;
	private String purpose;
	private String phoneCountryCode;

	public UserDTO() {
	}

	@Builder
	public UserDTO(Long userIdx, String email, String name, String password, Boolean isAcceptEmail, String phone, int salary, String salaryPeriod, String salaryCurrency, int careerPeriod, String searchStatus, Long recommenderIdx, String profileImage, int profileLevel, String purpose, String phoneCountryCode) {
		this.userIdx = userIdx;
		this.email = email;
		this.name = name;
		this.password = password;
		this.isAcceptEmail = isAcceptEmail;
		this.phone = phone;
		this.salary = salary;
		this.salaryPeriod = salaryPeriod;
		this.salaryCurrency = salaryCurrency;
		this.careerPeriod = careerPeriod;
		this.searchStatus = searchStatus;
		this.recommenderIdx = recommenderIdx;
		this.profileImage = profileImage;
		this.profileLevel = profileLevel;
		this.purpose = purpose;
		this.phoneCountryCode = phoneCountryCode;
	}
}
