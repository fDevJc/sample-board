package com.jc.callbustask.acceptance;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jc.callbustask.dto.request.AddPostRequest;
import com.jc.callbustask.dto.request.ModifyPostRequest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PostAcceptanceTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	private final String API_VERSION = "/api/v1";
	private final String POST_URI = API_VERSION + "/posts";
	private final String MODIFY_POST_URI = API_VERSION + "/posts/{postId}";
	private final String LIKE_POST_URI = API_VERSION + "/posts/{postId}/like";

	private final String AUTHENTICATION_HEADER = "Authentication";
	private final String REALTOR_ACCOUNT = "Realtor 47";
	private final String LESSOR_ACCOUNT = "Lessor 21";
	private final String LESSEE_ACCOUNT = "Lessee 562";

	private final AddPostRequest addPostRequest = new AddPostRequest("title", "content");
	private final ModifyPostRequest modifyPostRequest = new ModifyPostRequest("modifiedTitle", "modifiedContent");

	@ParameterizedTest
	@ValueSource(strings = {REALTOR_ACCOUNT, LESSOR_ACCOUNT, LESSEE_ACCOUNT})
	void 내부사용자계정으로_글목록을_조회할수있다(String authenticationHeaderValue) throws Exception {
		//when
		ResultActions result = mockMvc.perform(
			get(POST_URI)
				.header(AUTHENTICATION_HEADER, authenticationHeaderValue)
				.accept(MediaType.APPLICATION_JSON)
		);

		//then
		result.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void 외부사용자로_글목록을_조회할수있다() throws Exception {
		//when
		ResultActions result = mockMvc.perform(
			get(POST_URI)
				.accept(MediaType.APPLICATION_JSON)
		);

		//then
		result.andExpect(status().isOk()).andDo(print());
	}

	@ParameterizedTest
	@ValueSource(strings = {REALTOR_ACCOUNT, LESSOR_ACCOUNT, LESSEE_ACCOUNT})
	void 내부사용자계정으로_글을_등록할수있다(String authenticationHeaderValue) throws Exception {
		//when
		ResultActions result = performAddPost(authenticationHeaderValue, addPostRequest);

		//then
		result.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	void 외부사용자로_글을_등록할수없다() throws Exception {
		//when
		ResultActions result = mockMvc.perform(
			post(POST_URI)
				.content(objectMapper.writeValueAsString(addPostRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		//then
		result.andExpect(status().isUnauthorized()).andDo(print());
	}

	@Test
	void 본인글을_수정할수있다() throws Exception {
		//given
		MvcResult mvcResult = performAddPost(REALTOR_ACCOUNT, addPostRequest).andReturn();
		String savedPostId = getPostId(mvcResult.getResponse().getContentAsString());

		//when
		ResultActions result = performModifyPost(savedPostId, REALTOR_ACCOUNT, modifyPostRequest);

		//then
		result.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void 타인글을_수정할수없다() throws Exception {
		//given
		MvcResult mvcResult = performAddPost(REALTOR_ACCOUNT, addPostRequest).andReturn();
		String savedPostId = getPostId(mvcResult.getResponse().getContentAsString());

		//when
		ResultActions result = performModifyPost(savedPostId, LESSEE_ACCOUNT, modifyPostRequest);

		//then
		result.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void 본인글을_삭제할수있다() throws Exception {
		//given
		MvcResult mvcResult = performAddPost(REALTOR_ACCOUNT, addPostRequest).andReturn();
		String savedPostId = getPostId(mvcResult.getResponse().getContentAsString());

		//when
		ResultActions result = performDeletePost(savedPostId, REALTOR_ACCOUNT);

		//then
		result.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void 타인글을_삭제할수없다() throws Exception {
		//given
		MvcResult mvcResult = performAddPost(REALTOR_ACCOUNT, addPostRequest).andReturn();
		String savedPostId = getPostId(mvcResult.getResponse().getContentAsString());

		//when
		ResultActions result = performDeletePost(savedPostId, LESSEE_ACCOUNT);

		//then
		result.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void 작성된글에_좋아요를_할수있다() throws Exception {
		//given
		MvcResult mvcResult = performAddPost(REALTOR_ACCOUNT, addPostRequest).andReturn();
		String savedPostId = getPostId(mvcResult.getResponse().getContentAsString());

		//when
		ResultActions result = performLikePost(savedPostId, LESSEE_ACCOUNT);
		//then
		result.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	void 작성된글에_좋아요를_두번이상_할수없다() throws Exception {
		//given
		MvcResult mvcResult = performAddPost(REALTOR_ACCOUNT, addPostRequest).andReturn();
		String savedPostId = getPostId(mvcResult.getResponse().getContentAsString());
		performLikePost(savedPostId, LESSEE_ACCOUNT);

		//when
		ResultActions result = performLikePost(savedPostId, LESSEE_ACCOUNT);
		//then
		result.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void 작성된글에_좋아요를_취소_할수있다() throws Exception {
		//given
		MvcResult mvcResult = performAddPost(REALTOR_ACCOUNT, addPostRequest).andReturn();
		String savedPostId = getPostId(mvcResult.getResponse().getContentAsString());

		//when
		performLikePost(savedPostId, LESSEE_ACCOUNT);
		ResultActions result = performUnLikePost(savedPostId, LESSEE_ACCOUNT);
		//then
		result.andExpect(status().isOk()).andDo(print());
	}

	private ResultActions performLikePost(String savedPostId, String authenticationHeaderValue) throws Exception {
		return mockMvc.perform(
			post(LIKE_POST_URI, savedPostId)
				.header(AUTHENTICATION_HEADER, authenticationHeaderValue)
				.accept(MediaType.APPLICATION_JSON)
		);
	}

	private ResultActions performUnLikePost(String savedPostId, String authenticationHeaderValue) throws Exception {
		return mockMvc.perform(
			delete(LIKE_POST_URI, savedPostId)
				.header(AUTHENTICATION_HEADER, authenticationHeaderValue)
				.accept(MediaType.APPLICATION_JSON)
		);
	}

	private ResultActions performDeletePost(String savedPostId, String authenticationHeaderValue) throws Exception {
		return mockMvc.perform(
			delete(MODIFY_POST_URI, savedPostId)
				.header(AUTHENTICATION_HEADER, authenticationHeaderValue)
				.accept(MediaType.APPLICATION_JSON)
		);
	}

	private ResultActions performAddPost(String authenticationHeaderValue, AddPostRequest request) throws Exception {
		return mockMvc.perform(
			post(POST_URI)
				.header(AUTHENTICATION_HEADER, authenticationHeaderValue)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);
	}

	private ResultActions performModifyPost(String savedPostId, String authenticationHeaderValue, ModifyPostRequest request) throws Exception {
		return mockMvc.perform(
			patch(MODIFY_POST_URI, savedPostId)
				.header(AUTHENTICATION_HEADER, authenticationHeaderValue)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);
	}

	private String getPostId(String responseBody) {
		int startIdx = responseBody.indexOf("id: ") + 3;
		int endIdx = responseBody.indexOf(" is created");
		return responseBody.substring(startIdx, endIdx).trim();
	}
}
