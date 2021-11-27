package com.example.demo.src.location.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetLocationRes {

	private String countryName;
	private String regionName;

	public GetLocationRes() {
	}

	public GetLocationRes(String countryName, String regionName) {
		this.countryName = countryName;
		this.regionName = regionName;
	}
}

