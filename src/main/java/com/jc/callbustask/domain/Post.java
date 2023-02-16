package com.jc.callbustask.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post extends BaseEntity {
	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private Member writer;

	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public static Post createPost(String title, String content) {
		return new Post(title, content);
	}
}
