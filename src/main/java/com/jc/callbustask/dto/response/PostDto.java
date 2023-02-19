package com.jc.callbustask.dto.response;

import com.jc.callbustask.domain.enums.AccountType;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class PostDto {
	private String title;
	private String accountIdAndAccountType;
	private int likeCount;
	private String likeStatus;

	@QueryProjection
	public PostDto(final String title, final String accountId, final AccountType accountType, final int likeCount, final String likeStatus) {
		this.title = title;
		this.accountIdAndAccountType = concat(accountId, accountType);
		this.likeCount = likeCount;
		this.likeStatus = likeStatus;
	}

	private String concat(String accountId, AccountType accountType) {
		return String.format("%s(%s)", accountId, accountType.getTypeName());
	}
}
