package com.example.demo.src.content.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LabelDTO {

	private Long labelId;
	private String name;

	@Builder
	public LabelDTO(Long labelId, String name) {
		this.labelId = labelId;
		this.name = name;
	}
}
