package com.jc.callbustask.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jc.callbustask.domain.Account;
import com.jc.callbustask.domain.Like;
import com.jc.callbustask.domain.Post;

public interface LikeRepository extends JpaRepository<Like, Long> {
	Optional<Like> findByAccountAndPost(Account account, Post post);
}
