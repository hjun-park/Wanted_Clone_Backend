package com.example.demo.src.company.model;

import lombok.Getter;

@Getter
public class GetCompanyNewsRes {

	private String title;
	private String media;
	private String url;

	public GetCompanyNewsRes() {
	}

	public GetCompanyNewsRes(String title, String media, String url) {
		this.title = title;
		this.media = media;
		this.url = url;
	}
}
