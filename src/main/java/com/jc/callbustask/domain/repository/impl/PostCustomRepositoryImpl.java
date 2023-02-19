package com.jc.callbustask.domain.repository.impl;

import static com.jc.callbustask.domain.QAccount.*;
import static com.jc.callbustask.domain.QLike.*;
import static com.jc.callbustask.domain.QPost.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Pageable;

import com.jc.callbustask.domain.repository.PostCustomRepository;
import com.jc.callbustask.dto.response.PostDto;
import com.jc.callbustask.dto.response.QPostDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class PostCustomRepositoryImpl implements PostCustomRepository {
	private final JPAQueryFactory queryFactory;

	public PostCustomRepositoryImpl(final EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<PostDto> findAllPosts(final String accountId, final Pageable page) {
		return queryFactory
			.select(
				new QPostDto(
					post.title,
					account.accountId,
					account.accountType,
					post.likeCount,
					like.id.stringValue()))
			.from(post)
			.join(post.writer, account)
			.leftJoin(like)
			.on(post.eq(like.post), account.accountId.eq(accountId))
			.offset(page.getOffset())
			.limit(page.getPageSize())
			.fetch();
	}
}
