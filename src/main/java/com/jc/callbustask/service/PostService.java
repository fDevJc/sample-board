package com.jc.callbustask.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.callbustask.domain.Account;
import com.jc.callbustask.domain.Like;
import com.jc.callbustask.domain.Post;
import com.jc.callbustask.domain.repository.AccountRepository;
import com.jc.callbustask.domain.repository.LikeRepository;
import com.jc.callbustask.domain.repository.PostRepository;
import com.jc.callbustask.dto.request.AddPostRequest;
import com.jc.callbustask.dto.request.ModifyPostRequest;
import com.jc.callbustask.dto.response.PostResponse;
import com.jc.callbustask.service.exception.NotFoundAccountException;
import com.jc.callbustask.service.exception.NotFoundPostException;
import com.jc.callbustask.service.exception.PostAuthorityException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
	private final PostRepository postRepository;
	private final AccountRepository accountRepository;
	private final LikeRepository likeRepository;

	public Long addPost(final String accountId, final AddPostRequest addPostRequest) {

		Account writer = accountRepository.findByAccountId(accountId)
			.orElseThrow(() -> new NotFoundAccountException(accountId));
		Post post = Post.createPost(writer, addPostRequest.getTitle(), addPostRequest.getContent());

		return postRepository.save(post).getId();
	}

	public void modifyPost(final String accountId, final long postId, final ModifyPostRequest request) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(postId));

		if (post.isWriter(accountId)) {
			post.updatePost(request.getTitle(), request.getContent());
		} else {
			throw new PostAuthorityException(accountId);
		}
	}

	public void deletePost(final String accountId, final long postId) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(postId));

		if (post.isWriter(accountId)) {
			post.deletePost();
		} else {
			throw new PostAuthorityException(accountId);
		}
	}

	/*
	 * todo
	 * likePost와 unLikePost 로직이 비슷한 부분이 많음
	 * 굳이 메소드를 나누는게 좋을지 고민 후 리팩토링
	 */
	public void likePost(final String accountId, final long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(postId));

		post.plusLikeCount();

		Account account = accountRepository.findByAccountId(accountId)
			.orElseThrow(() -> new NotFoundAccountException(accountId));

		if (likeRepository.findByAccountAndPost(account, post).isPresent()) {
			throw new RuntimeException();
		}

		Like like = Like.createLike(account, post);

		likeRepository.save(like);

	}

	public void unLikePost(final String accountId, final long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(postId));

		post.minusLikeCount();

		Account account = accountRepository.findByAccountId(accountId)
			.orElseThrow(() -> new NotFoundAccountException(accountId));

		Like like = likeRepository.findByAccountAndPost(account, post)
			.orElseThrow(() -> new RuntimeException());

		likeRepository.delete(like);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> findAllPosts(String accountId, Pageable page) {
		return postRepository.findAllPosts(accountId, page);
	}
}
