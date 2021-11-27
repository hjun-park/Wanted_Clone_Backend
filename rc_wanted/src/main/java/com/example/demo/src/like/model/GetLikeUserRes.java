package com.example.demo.src.like.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetLikeUserRes {

	private int userIdx;
	private String userImage;
	private String userName;

	public GetLikeUserRes() {
	}

	public GetLikeUserRes(int userIdx, String userImage, String userName) {
		this.userIdx = userIdx;
		this.userImage = userImage;
		this.userName = userName;
	}
}
