package com.jc.callbustask.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import com.jc.callbustask.domain.enums.AccountType;
import com.jc.callbustask.domain.enums.PostStatus;
import com.jc.callbustask.domain.exception.CannotLessThanZeroException;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PostTest {
	private final Account writer = Account.createAccount("nickname", AccountType.LESSEE, "accountId");
	private final Post post = Post.createPost(writer, "title", "content");

	@Test
	void post의_제목내용을_수정할수있다() {
		//given
		String updateTitle = "updateTitle";
		String updateContent = "updateContent";

		//when
		post.updatePost(updateTitle, updateContent);

		//then
		assertThat(post.getTitle()).isEqualTo(updateTitle);
		assertThat(post.getContent()).isEqualTo(updateContent);
	}

	@Test
	void post의_상태를_삭제상태로_수정할수있다() {
		//when
		post.deletePost();

		//then
		PostStatus result = post.getPostStatus();
		assertThat(result).isEqualTo(PostStatus.DELETE);
	}

	@Test
	void post의_작성자를_확인할수있다() {
		//given
		String writerAccountId = "accountId";

		//when
		boolean result = post.isWriter(writerAccountId);

		//then
		assertThat(result).isEqualTo(true);
	}

	@Test
	void post의_좋아요횟수를_증가감소시킬수있다() {
		//when
		int initResult = post.getLikeCount();
		post.plusLikeCount();
		int plusResult = post.getLikeCount();
		post.minusLikeCount();
		int minusResult = post.getLikeCount();

		//then
		assertThat(initResult).isEqualTo(0);
		assertThat(plusResult).isEqualTo(1);
		assertThat(minusResult).isEqualTo(0);
	}

	@Test
	void post의_좋아요횟수는_0보다작을수없다_CannotLessThanZeroException_발생() {
		assertThrows(
			CannotLessThanZeroException.class,
			() -> post.minusLikeCount()
		);
	}
}