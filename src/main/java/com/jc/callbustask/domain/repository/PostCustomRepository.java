package com.jc.callbustask.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jc.callbustask.dto.response.PostDto;

public interface PostCustomRepository {

	List<PostDto> findAllPosts(String accountId, Pageable page);
}
