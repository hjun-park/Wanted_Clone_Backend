package com.example.demo.src.tag.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

	private Long tagIdx;
	private Long tagCategoryIdx;
	private String name;

	@Builder
	public Tag(Long tagIdx, Long tagCategoryIdx, String name) {
		this.tagIdx = tagIdx;
		this.tagCategoryIdx = tagCategoryIdx;
		this.name = name;
	}
}
