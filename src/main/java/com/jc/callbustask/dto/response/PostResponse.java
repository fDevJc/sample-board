package com.jc.callbustask.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class PostResponse {
	private String title;
	private String accountIdAndAccountType;
	private int likeCount;
	private String likeStatus;

	@QueryProjection
	public PostResponse(String title, String accountIdAndAccountType, int likeCount, String likeStatus) {
		this.title = title;
		this.accountIdAndAccountType = accountIdAndAccountType;
		this.likeCount = likeCount;
		this.likeStatus = likeStatus;
	}
}
