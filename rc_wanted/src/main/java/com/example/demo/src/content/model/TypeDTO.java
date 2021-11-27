package com.example.demo.src.content.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TypeDTO {

	private Long typeId;
	private String name;

	@Builder
	public TypeDTO(Long typeId, String name) {
		this.typeId = typeId;
		this.name = name;
	}
}
