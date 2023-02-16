package com.jc.callbustask.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jc.callbustask.config.auth.annotation.Authority;
import com.jc.callbustask.dto.request.AddPostRequest;
import com.jc.callbustask.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostController {
	private final PostService postService;

	@GetMapping("/api/v1/check")
	public void check() {
		System.out.println("check");
	}

	@Authority
	@PostMapping("/api/v1/posts")
	public void addPost(
		@RequestBody @Validated final AddPostRequest addPostRequest
	) {
		postService.addPost(addPostRequest);
	}
}
