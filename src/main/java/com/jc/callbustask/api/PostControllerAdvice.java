package com.jc.callbustask.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jc.callbustask.config.auth.exception.InvalidMemberTypeAccessException;
import com.jc.callbustask.domain.exception.CannotLessThanZeroException;
import com.jc.callbustask.service.exception.NotFoundAccountException;
import com.jc.callbustask.service.exception.NotFoundLikeException;
import com.jc.callbustask.service.exception.NotFoundPostException;
import com.jc.callbustask.service.exception.PostAuthorityException;
import com.jc.callbustask.service.exception.PostLikeCountExceedException;

@RestControllerAdvice
public class PostControllerAdvice {

	@ExceptionHandler(InvalidMemberTypeAccessException.class)
	public ResponseEntity invalidMemberTypeAccessException(final InvalidMemberTypeAccessException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	@ExceptionHandler(PostAuthorityException.class)
	public ResponseEntity postAuthorityException(final PostAuthorityException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(PostLikeCountExceedException.class)
	public ResponseEntity postLikeCountExceedException(final PostLikeCountExceedException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(NotFoundLikeException.class)
	public ResponseEntity notFoundLikeException(final NotFoundLikeException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(CannotLessThanZeroException.class)
	public ResponseEntity cannotLessThanZeroException(final CannotLessThanZeroException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(NotFoundAccountException.class)
	public ResponseEntity notFoundAccountException(final NotFoundAccountException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(NotFoundPostException.class)
	public ResponseEntity notFoundPostException(final NotFoundPostException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}
