package com.example.demo.src.content.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetContentDetailRes {

	private String title;
	private String content;

	public GetContentDetailRes() {
	}

	public GetContentDetailRes(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
