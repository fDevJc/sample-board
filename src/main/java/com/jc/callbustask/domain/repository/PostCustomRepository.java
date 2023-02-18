package com.jc.callbustask.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jc.callbustask.dto.response.PostResponse;

public interface PostCustomRepository {

	List<PostResponse> findAllPosts(String accountId, Pageable page);
}
