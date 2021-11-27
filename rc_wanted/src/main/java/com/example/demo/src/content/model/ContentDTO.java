package com.example.demo.src.content.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentDTO {

	private Long contentId;
	private Long contentCategoryId;
	private String title;
	private Date startDate;
	private String content;

	private String isFree;
	private String titleImage;
	private Date endDate;
	private Boolean isAllTime;
	private Long typeId;

	@Builder
	public ContentDTO(Long contentId, Long contentCategoryId, String title, Date startDate, String content, String isFree, String titleImage, Date endDate, Boolean isAllTime, Long typeId) {
		this.contentId = contentId;
		this.contentCategoryId = contentCategoryId;
		this.title = title;
		this.startDate = startDate;
		this.content = content;
		this.isFree = isFree;
		this.titleImage = titleImage;
		this.endDate = endDate;
		this.isAllTime = isAllTime;
		this.typeId = typeId;
	}
}
