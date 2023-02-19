package com.jc.callbustask.domain.exception;

public class CannotLessThanZeroException extends RuntimeException {
	private static final String MESSAGE_FORMAT = "Like count cannot be less than zero";

	public CannotLessThanZeroException() {
		super(MESSAGE_FORMAT);
	}
}
