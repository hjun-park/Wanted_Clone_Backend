package com.example.demo.src.region.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

	private Long regionIdx;
	private String name_kor;
	private String name_eng;

	@Builder
	public Region(Long regionIdx, String name_kor, String name_eng) {
		this.regionIdx = regionIdx;
		this.name_kor = name_kor;
		this.name_eng = name_eng;
	}
}
