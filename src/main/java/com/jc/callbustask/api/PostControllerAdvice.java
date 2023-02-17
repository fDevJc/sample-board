package com.jc.callbustask.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jc.callbustask.config.auth.exception.InvalidMemberTypeAccessException;
import com.jc.callbustask.service.exception.PostModificationException;

@RestControllerAdvice
public class PostControllerAdvice {

	@ExceptionHandler(InvalidMemberTypeAccessException.class)
	public ResponseEntity invalidMemberTypeAccessException(InvalidMemberTypeAccessException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	@ExceptionHandler(PostModificationException.class)
	public ResponseEntity postModificationException(PostModificationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}
