package com.jc.callbustask.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jc.callbustask.domain.enums.PostStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Post extends BaseEntity {
	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private Account writer;

	private LocalDateTime deletedDate;
	@Enumerated(value = EnumType.STRING)
	private PostStatus postStatus;

	private Post(Account writer, String title, String content, PostStatus postStatus) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.postStatus = postStatus;
	}

	public static Post createPost(Account writer, String title, String content) {
		return new Post(writer, title, content, PostStatus.ACTIVE);
	}

	public void updatePost(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public boolean isWriter(String accountId) {
		return writer.isMyself(accountId);
	}

	public void deletePost() {
		this.postStatus = PostStatus.DELETE;
		this.deletedDate = LocalDateTime.now();
	}
}
