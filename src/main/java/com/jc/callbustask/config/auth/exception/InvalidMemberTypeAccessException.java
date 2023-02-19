package com.jc.callbustask.config.auth.exception;

public class InvalidMemberTypeAccessException extends RuntimeException {
	private static final String MESSAGE_FORMAT = "Don't have permission for that request";

	public InvalidMemberTypeAccessException() {
		super(MESSAGE_FORMAT);
	}
}
