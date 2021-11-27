package com.example.demo.src.company.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagCompany {

	private Long tagCompanyIdx;
	private Long tagIdx;
	private Long companyIdx;

	@Builder
	public TagCompany(Long tagIdx, Long companyIdx) {
		this.tagIdx = tagIdx;
		this.companyIdx = companyIdx;
	}
}
