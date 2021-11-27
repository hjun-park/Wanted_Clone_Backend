package com.example.demo.src.location.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetLocationIdRes {

	private Long countryId;
	private Long regionId;

	public GetLocationIdRes() {
	}

	public GetLocationIdRes(Long countryId, Long regionId) {
		this.countryId = countryId;
		this.regionId = regionId;
	}
}
