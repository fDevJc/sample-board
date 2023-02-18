package com.jc.callbustask.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jc.callbustask.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
}
