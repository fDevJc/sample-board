package com.jc.callbustask.domain.enums;

public enum PostStatus {
	ACTIVE("삭제되지 않은 게시글"), DELETE("삭제된 게시글");

	private String description;
	PostStatus(String description) {
		this.description = description;
	}
}
