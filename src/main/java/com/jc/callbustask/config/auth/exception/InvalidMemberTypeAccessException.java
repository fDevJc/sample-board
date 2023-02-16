package com.jc.callbustask.config.auth.exception;

public class InvalidMemberTypeAccessException extends RuntimeException {
	private static final String MESSAGE_FORMAT = "해당 요청의 권한이 없습니다";
	public InvalidMemberTypeAccessException() {
		super(MESSAGE_FORMAT);
	}
}
