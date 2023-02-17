package com.jc.callbustask.service.exception;

public class NotFoundAccountException extends RuntimeException {
	private static final String MESSAGE_FORMAT = "Can not found Account(Account Id: %s)";

	public NotFoundAccountException(final String accountId) {
		super(String.format(MESSAGE_FORMAT, accountId));
	}
}
