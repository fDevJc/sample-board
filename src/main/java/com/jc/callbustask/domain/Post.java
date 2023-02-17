package com.jc.callbustask.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Post extends BaseEntity {
	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private Account writer;

	public Post(Account writer, String title, String content) {
		this.writer = writer;
		this.title = title;
		this.content = content;
	}

	public static Post createPost(Account writer, String title, String content) {
		return new Post(writer, title, content);
	}

	public void updatePost(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public boolean isWriter(String accountId) {
		return writer.isMyself(accountId);
	}
}
