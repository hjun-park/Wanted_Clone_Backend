package com.example.demo.src.bookmark.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkDTO {

	private Long bookmarkIdx;
	private Long userIdx;
	private Long recruitIdx;

	public BookmarkDTO() {
	}

	@Builder
	public BookmarkDTO(Long bookmarkIdx, Long userIdx, Long recruitIdx) {
		this.bookmarkIdx = bookmarkIdx;
		this.userIdx = userIdx;
		this.recruitIdx = recruitIdx;
	}
}
