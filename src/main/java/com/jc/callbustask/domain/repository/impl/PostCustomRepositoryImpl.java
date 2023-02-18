package com.jc.callbustask.domain.repository.impl;

import static com.jc.callbustask.domain.QAccount.*;
import static com.jc.callbustask.domain.QLike.*;
import static com.jc.callbustask.domain.QPost.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Pageable;

import com.jc.callbustask.domain.repository.PostCustomRepository;
import com.jc.callbustask.dto.response.PostResponse;
import com.jc.callbustask.dto.response.QPostResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class PostCustomRepositoryImpl implements PostCustomRepository {
	private final JPAQueryFactory queryFactory;

	public PostCustomRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<PostResponse> findAllPosts(String accountId, Pageable page) {
		return queryFactory
			.select(
				new QPostResponse(
					post.title,
					account.accountType.stringValue(),
					post.likeCount,
					like.id.stringValue()
				))
			.from(post)
			.join(post.writer, account)
			.leftJoin(like)
			.on(post.eq(like.post))
			.on(eqLikeAccountId(accountId))
			.fetch();
	}

	private static BooleanExpression eqLikeAccountId(String accountId) {
		if (accountId == null) {
			return null;
		}
		return account.accountId.eq(accountId);
	}
}
