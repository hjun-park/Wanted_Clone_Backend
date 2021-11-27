package com.example.demo.src.country.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CountryDTO {

	private Long countryIdx;
	private String name_kor;
	private String name_eng;

	@Builder
	public CountryDTO(Long countryIdx, String name_kor, String name_eng) {
		this.countryIdx = countryIdx;
		this.name_kor = name_kor;
		this.name_eng = name_eng;
	}
}
