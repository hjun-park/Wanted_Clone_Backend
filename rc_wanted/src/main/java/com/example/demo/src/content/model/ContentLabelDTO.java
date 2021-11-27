package com.example.demo.src.content.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentLabelDTO {

	private Long contentLabelId;
	private Long contentId;
	private Long labelId;

	@Builder
	public ContentLabelDTO(Long contentLabelId, Long contentId, Long labelId) {
		this.contentLabelId = contentLabelId;
		this.contentId = contentId;
		this.labelId = labelId;
	}
}
