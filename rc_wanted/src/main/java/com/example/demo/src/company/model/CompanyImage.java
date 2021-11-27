package com.example.demo.src.company.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyImage {

	private Long companyImageIdx;
	private Long companyIdx;
	private String image;

	@Builder
	public CompanyImage(Long companyIdx, String image) {
		this.companyIdx = companyIdx;
		this.image = image;
	}
}
