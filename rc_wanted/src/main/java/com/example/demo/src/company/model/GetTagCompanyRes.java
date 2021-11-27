package com.example.demo.src.company.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetTagCompanyRes {

	private String logo;
	private String name;
	private List<String> tagNames;

	public GetTagCompanyRes() {
	}

	public GetTagCompanyRes(String logo, String name, List<String> tagNames) {
		this.logo = logo;
		this.name = name;
		this.tagNames = tagNames;
	}
}
