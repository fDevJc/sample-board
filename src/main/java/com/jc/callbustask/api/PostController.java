package com.jc.callbustask.api;

import static com.jc.callbustask.dto.response.ApiResponse.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jc.callbustask.config.auth.annotation.AccountId;
import com.jc.callbustask.config.auth.annotation.Authority;
import com.jc.callbustask.dto.request.AddPostRequest;
import com.jc.callbustask.dto.request.ModifyPostRequest;
import com.jc.callbustask.dto.response.ApiResponse;
import com.jc.callbustask.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostController {
	private final PostService postService;

	@Authority
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/posts")
	public ApiResponse addPost(
		@AccountId final String accountId,
		@RequestBody @Validated final AddPostRequest request
	) {
		return toResponse(POST_CREATE_MESSAGE, postService.addPost(accountId, request));
	}

	@Authority
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/api/v1/posts/{postId}")
	public ApiResponse modifyPost(
		@AccountId final String accountId,
		@PathVariable final long postId,
		@RequestBody @Validated final ModifyPostRequest request
	) {
		return toResponse(POST_MODIFY_MESSAGE, postService.modifyPost(accountId, postId, request));
	}

	@Authority
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/api/v1/posts/{postId}")
	public ApiResponse deletePost(
		@AccountId final String accountId,
		@PathVariable final long postId
	) {
		return toResponse(POST_DELETE_MESSAGE, postService.deletePost(accountId, postId));
	}

	@Authority
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/posts/{postId}/like")
	public ApiResponse likePost(
		@AccountId final String accountId,
		@PathVariable final long postId
	) {
		return toResponse(LIKE_CREATE_MESSAGE, postService.likePost(accountId, postId));

	}

	@Authority
	@DeleteMapping("/api/v1/posts/{postId}/like")
	public ApiResponse unLikePost(
		@AccountId final String accountId,
		@PathVariable final long postId
	) {
		return toResponse(LIKE_DELETE_MESSAGE, postService.unLikePost(accountId, postId));

	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/api/v1/posts")
	public ResponseEntity findAllPosts(
		@AccountId final String accountId,
		@PageableDefault final Pageable page
	) {
		return ResponseEntity.ok().body(postService.findAllPosts(accountId, page));
	}
}
