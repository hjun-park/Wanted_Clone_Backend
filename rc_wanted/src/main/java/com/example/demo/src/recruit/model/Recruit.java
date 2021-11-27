package com.example.demo.src.recruit.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruit {

	private Long recruitIdx;
	private Long companyIdx;
	private Long jobIdx;
	private String title;
	private int likeCount;

	private String content;
	private Date deadline;
	private int reward;
	private int minExperience;
	private Boolean isAlways;

	@Builder
	public Recruit(Long recruitIdx, Long companyIdx, Long jobIdx, String title, int likeCount, String content, Date deadline, int reward, int minExperience, Boolean isAlways) {
		this.recruitIdx = recruitIdx;
		this.companyIdx = companyIdx;
		this.jobIdx = jobIdx;
		this.title = title;
		this.likeCount = likeCount;
		this.content = content;
		this.deadline = deadline;
		this.reward = reward;
		this.minExperience = minExperience;
		this.isAlways = isAlways;
	}
}
