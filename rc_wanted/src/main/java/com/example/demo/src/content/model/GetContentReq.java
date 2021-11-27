package com.example.demo.src.content.model;

import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class GetContentReq {

	@Positive
	private Long typeId;

	@Positive
	private Long labelId;

	private String pay;

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}
}
