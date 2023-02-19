package com.jc.callbustask.dto.response;

import lombok.Getter;

@Getter
public class ApiResponse {
	public static final String POST_CREATE_MESSAGE = "Post id: %s is created";
	public static final String POST_MODIFY_MESSAGE = "Post id: %s is modified";
	public static final String POST_DELETE_MESSAGE = "Post id: %s is deleted";
	public static final String LIKE_CREATE_MESSAGE = "Like id: %s is created";
	public static final String LIKE_DELETE_MESSAGE = "Like id: %s is delete";

	private String message;
	private Object data;

	private ApiResponse(String message, Object data) {
		this.message = message;
		this.data = data;
	}

	public static ApiResponse toResponse(String message, Long id) {
		return new ApiResponse(String.format(message, id), null);
	}
}
