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
import com.jc.callbustask.dto.response.PostDto;
import com.jc.callbustask.service.exception.NotFoundAccountException;
import com.jc.callbustask.service.exception.NotFoundLikeException;
import com.jc.callbustask.service.exception.NotFoundPostException;
import com.jc.callbustask.service.exception.PostAuthorityException;
import com.jc.callbustask.service.exception.PostLikeCountExceedException;

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

	public Long modifyPost(final String accountId, final long postId, final ModifyPostRequest request) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(postId));

		if (post.isWriter(accountId)) {
			post.updatePost(request.getTitle(), request.getContent());
			return post.getId();
		} else {
			throw new PostAuthorityException(accountId);
		}
	}

	public Long deletePost(final String accountId, final long postId) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(postId));

		/*
			가독성 측면에서 부정적 조건문보다 긍정적 조건문을 사용하는게 좋겠다고 생각했는데
			예외 발생조건이 더 빠르게 눈에 들어오는 방식이 더 좋은거같다는 의견
		 */
		// if (post.isWriter(accountId)) {
		// 	post.deletePost();
		// 	return post.getId();
		// } else {
		// 	throw new PostAuthorityException(accountId);
		// }
		if (!post.isWriter(accountId)) {
			throw new PostAuthorityException(accountId);
		}
		post.deletePost();
		return post.getId();
	}


	public Long likePost(final String accountId, final long postId) {
		//연관성있는 로직은 모아서 각 로직을 구분한다면 의미와 목적이 명확

		//조회
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(postId));
		Account account = accountRepository.findByAccountId(accountId)
			.orElseThrow(() -> new NotFoundAccountException(accountId));
		//검증
		if (likeRepository.findByAccountAndPost(account, post).isPresent()) {
			throw new PostLikeCountExceedException(accountId, postId);
		}
		//명령
		post.plusLikeCount();
		Like like = Like.createLike(account, post);
		return likeRepository.save(like).getId();
	}

	public Long unLikePost(final String accountId, final long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(postId));

		post.minusLikeCount();

		Account account = accountRepository.findByAccountId(accountId)
			.orElseThrow(() -> new NotFoundAccountException(accountId));

		Like like = likeRepository.findByAccountAndPost(account, post)
			.orElseThrow(() -> new NotFoundLikeException(accountId, postId));

		likeRepository.delete(like);

		return postId;
	}

	@Transactional(readOnly = true)
	public List<PostDto> findAllPosts(final String accountId, final Pageable page) {
		return postRepository.findAllPosts(accountId, page);
	}
}
