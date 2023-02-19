package com.jc.callbustask.service.exception;

public class PostLikeCountExceedException extends RuntimeException {
	private static final String MESSAGE_FORMAT = "Account(id: %s) already like post(id: %d)";

	public PostLikeCountExceedException(final String accountId, long post) {
		super(String.format(MESSAGE_FORMAT, accountId, post));
	}
}
