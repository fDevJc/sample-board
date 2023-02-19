package com.jc.callbustask.service.exception;

public class NotFoundLikeException extends RuntimeException {
	private static final String MESSAGE_FORMAT = "Can not found Like(AccountId: %s, PostId: %d)";

	public NotFoundLikeException(final String accountId, final long post) {
		super(String.format(MESSAGE_FORMAT, accountId, post));
	}
}
