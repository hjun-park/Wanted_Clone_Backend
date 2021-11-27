package com.example.demo.src.company.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetSearchCompanyRes {

	private Long companyId;
	private String companyLogo;
	private String companyName;
	private int responseRate;

	public GetSearchCompanyRes() {
	}

	public GetSearchCompanyRes(Long companyId, String companyLogo, String companyName, int responseRate) {
		this.companyId = companyId;
		this.companyLogo = companyLogo;
		this.companyName = companyName;
		this.responseRate = responseRate;
	}
}
