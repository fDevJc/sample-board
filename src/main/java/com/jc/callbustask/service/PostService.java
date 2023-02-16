package com.jc.callbustask.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.callbustask.domain.Post;
import com.jc.callbustask.domain.repository.PostRepository;
import com.jc.callbustask.dto.request.AddPostRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository postRepository;

	@Transactional
	public Long addPost(AddPostRequest addPostRequest) {
		return postRepository.save(Post.createPost(addPostRequest.getTitle(), addPostRequest.getContent())).getId();
	}
}
