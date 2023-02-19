package com.jc.callbustask.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.jc.callbustask.domain.enums.PostStatus;
import com.jc.callbustask.domain.exception.CannotLessThanZeroException;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {
	private String title;

	@Lob
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private Account writer;

	private LocalDateTime deletedDate;

	@Enumerated(value = EnumType.STRING)
	private PostStatus postStatus;

	private int likeCount = 0;

	private Post(final Account writer, final String title, final String content, final PostStatus postStatus) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.postStatus = postStatus;
	}

	public static Post createPost(final Account writer, final String title, final String content) {
		return new Post(writer, title, content, PostStatus.ACTIVE);
	}

	public void updatePost(final String title, final String content) {
		this.title = title;
		this.content = content;
	}

	public boolean isWriter(final String accountId) {
		return writer.isMyself(accountId);
	}

	public void deletePost() {
		this.postStatus = PostStatus.DELETE;
		this.deletedDate = LocalDateTime.now();
	}

	public void plusLikeCount() {
		likeCount++;
	}

	public void minusLikeCount() {
		if (likeCount == 0) {
			throw new CannotLessThanZeroException();
		}
		likeCount--;
	}
}
