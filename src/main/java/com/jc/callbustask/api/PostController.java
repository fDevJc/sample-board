package com.jc.callbustask.api;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jc.callbustask.config.auth.annotation.Authority;
import com.jc.callbustask.config.auth.context.AuthenticationContextHolder;
import com.jc.callbustask.dto.request.AddPostRequest;
import com.jc.callbustask.dto.request.ModifyPostRequest;
import com.jc.callbustask.dto.response.PostResponse;
import com.jc.callbustask.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostController {
	private final PostService postService;

	@Authority
	@PostMapping("/api/v1/posts")
	public void addPost(
		@RequestBody @Validated final AddPostRequest request
	) {
		String accountId = AuthenticationContextHolder.CONTEXT.get();
		postService.addPost(accountId, request);
	}

	@Authority
	@PatchMapping("/api/v1/posts/{postId}")
	public void modifyPost(
		@PathVariable final long postId,
		@RequestBody @Validated final ModifyPostRequest request
	) {
		String accountId = AuthenticationContextHolder.CONTEXT.get();
		postService.modifyPost(accountId, postId, request);
	}

	@Authority
	@DeleteMapping("/api/v1/posts/{postId}")
	public void deletePost(
		@PathVariable final long postId
	) {
		String accountId = AuthenticationContextHolder.CONTEXT.get();
		postService.deletePost(accountId, postId);
	}

	@Authority
	@PostMapping("/api/v1/posts/{postId}/likes")
	public void likePost(
		@PathVariable final long postId
	) {
		String accountId = AuthenticationContextHolder.CONTEXT.get();
		postService.likePost(accountId, postId);
	}

	@Authority
	@DeleteMapping("/api/v1/posts/{postId}/likes")
	public void unLikePost(
		@PathVariable final long postId
	) {
		String accountId = AuthenticationContextHolder.CONTEXT.get();
		postService.unLikePost(accountId, postId);
	}

	@Authority
	@GetMapping("/api/v1/posts")
	public ResponseEntity findAllPosts(
		@PageableDefault Pageable page
	) {
		String accountId = AuthenticationContextHolder.CONTEXT.get();
		List<PostResponse> allPosts = postService.findAllPosts(accountId, page);
		return ResponseEntity.ok().body(allPosts);
	}
}
