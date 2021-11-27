package com.example.demo.src.tag.model;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostTagReq {

	@NotBlank
	private String tagName;

}
