package com.jc.callbustask.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import com.jc.callbustask.domain.enums.AccountType;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountTest {

	@Test
	void 계정아이디가_동일한지_확인한다() {
		//given
		String accountId = "sampleId";
		Account account = Account.createAccount("nickname", AccountType.LESSEE, accountId);

		//when
		boolean result = account.isMyself(accountId);

		//then
		assertThat(result).isEqualTo(true);
	}
}