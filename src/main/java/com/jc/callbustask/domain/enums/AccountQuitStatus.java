package com.jc.callbustask.domain.enums;

public enum AccountQuitStatus {
	QUIT("탈퇴 상태"), ACTIVE("활성 상태");

	private String description;

	AccountQuitStatus(String description) {
		this.description = description;
	}
}
