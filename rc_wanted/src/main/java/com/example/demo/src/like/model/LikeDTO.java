package com.example.demo.src.like.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeDTO {

	private Long likeIdx;
	private int userIdx;
	private Long recruitIdx;

	public LikeDTO() {
	}

	@Builder
	public LikeDTO(Long likeIdx, int userIdx, Long recruitIdx) {
		this.likeIdx = likeIdx;
		this.userIdx = userIdx;
		this.recruitIdx = recruitIdx;
	}
}
