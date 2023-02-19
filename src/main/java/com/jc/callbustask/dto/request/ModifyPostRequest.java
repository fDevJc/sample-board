package com.jc.callbustask.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ModifyPostRequest {
	@NotNull
	private String title;
	@NotNull
	private String content;

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
}
