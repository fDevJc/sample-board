package com.jc.callbustask.service.exception;

public class PostAuthorityException extends RuntimeException {
	private static final String MESSAGE_FORMAT = "Only the writer can modify this post(current accountId: %s)";

	public PostAuthorityException(String accountId) {
		super(String.format(MESSAGE_FORMAT, accountId));
	}
}
