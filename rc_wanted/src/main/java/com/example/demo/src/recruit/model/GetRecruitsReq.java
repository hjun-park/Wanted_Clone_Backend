package com.example.demo.src.recruit.model;

import lombok.Getter;

import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
public class GetRecruitsReq implements Serializable {

	private String country;

	private Integer year;

	@Positive
	private Long tagId;

	private String location;

	public GetRecruitsReq() {
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
