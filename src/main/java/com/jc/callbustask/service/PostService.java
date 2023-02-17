package com.jc.callbustask.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.callbustask.domain.Account;
import com.jc.callbustask.domain.Post;
import com.jc.callbustask.domain.repository.AccountRepository;
import com.jc.callbustask.domain.repository.PostRepository;
import com.jc.callbustask.dto.request.AddPostRequest;
import com.jc.callbustask.dto.request.ModifyPostRequest;
import com.jc.callbustask.service.exception.NotFoundAccountException;
import com.jc.callbustask.service.exception.NotFoundPostException;
import com.jc.callbustask.service.exception.PostAuthorityException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository postRepository;
	private final AccountRepository accountRepository;

	@Transactional
	public Long addPost(final String accountId, final AddPostRequest addPostRequest) {

		Account writer = accountRepository.findByAccountId(accountId)
			.orElseThrow(() -> new NotFoundAccountException(accountId));

		return postRepository.save(Post.createPost(writer, addPostRequest.getTitle(), addPostRequest.getContent())).getId();
	}

	@Transactional
	public void modifyPost(final String accountId, final long postId, final ModifyPostRequest request) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(postId));

		if (post.isWriter(accountId)) {
			post.updatePost(request.getTitle(), request.getContent());
		} else {
			throw new PostAuthorityException(accountId);
		}
	}

	@Transactional
	public void deletePost(final String accountId, final long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(postId));

		if (post.isWriter(accountId)) {
			post.deletePost();
		} else {
			throw new PostAuthorityException(accountId);
		}
	}
}
