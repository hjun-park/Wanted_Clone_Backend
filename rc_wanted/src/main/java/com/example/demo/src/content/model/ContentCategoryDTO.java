package com.example.demo.src.content.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentCategoryDTO {

	private Long contentCategoryId;
	private String name;

	@Builder
	public ContentCategoryDTO(Long contentCategoryId, String name) {
		this.contentCategoryId = contentCategoryId;
		this.name = name;
	}
}
