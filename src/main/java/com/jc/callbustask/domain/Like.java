package com.jc.callbustask.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "likes")
@Entity
public class Like extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	private Like(final Account account, final Post post) {
		this.account = account;
		this.post = post;
	}

	public static Like createLike(final Account account, final Post post) {
		return new Like(account, post);
	}
}
