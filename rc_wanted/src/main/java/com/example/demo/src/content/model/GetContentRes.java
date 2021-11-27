package com.example.demo.src.content.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetContentRes {

	private String titleImage;
	private String type;
	private String title;
	private Date startDate;
	private Date endDate;
	private Boolean isAllTime;
	private List<String> labels;

	public GetContentRes() {
	}

	public GetContentRes(String titleImage, String type, String title, Date startDate, Date endDate, Boolean isAllTime, List<String> labels) {
		this.titleImage = titleImage;
		this.type = type;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isAllTime = isAllTime;
		this.labels = labels;
	}
}
