package com.jc.callbustask.service.exception;

public class NotFoundPostException extends RuntimeException {
	private static final String MESSAGE_FORMAT = "Can not found Post(PostId: %s)";

	public NotFoundPostException(final long postId) {
		super(String.format(MESSAGE_FORMAT, postId));
	}
}
