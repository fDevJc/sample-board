package com.jc.callbustask.service.exception;

public class PostModificationException extends RuntimeException {
	private static final String MESSAGE_FORMAT = "Only the writer can modify this post(current accountId: %s)";

	public PostModificationException(String accountId) {
		super(String.format(MESSAGE_FORMAT, accountId));
	}
}
