package com.example.demo.src.content.model;

import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Getter
public class PostContentReq {

	@Positive @NotNull
	private Long contentCategoryId;

	@NotBlank
	private String title;

	@Future
	private Date startDate;

	@NotBlank
	private String content;

	@NotBlank
	private String isFree;

	private String titleImage;

	@Future
	private Date endDate;

	private Boolean isAllTime;

	@Positive @NotNull
	private Long typeId;

}
