package com.jc.callbustask.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.jc.callbustask.domain.enums.AccountQuitStatus;
import com.jc.callbustask.domain.enums.AccountType;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Account extends BaseEntity {
	private String nickname;

	@Enumerated(value = EnumType.STRING)
	private AccountType accountType;

	@Column(unique = true)
	private String accountId;

	@Enumerated(value = EnumType.STRING)
	private AccountQuitStatus quit;

	private Account(final String nickname, final AccountType accountType, final String accountId) {
		this.nickname = nickname;
		this.accountType = accountType;
		this.accountId = accountId;
		this.quit = AccountQuitStatus.ACTIVE;
	}

	public static Account createAccount(final String nickname, final AccountType accountType, final String accountId) {
		return new Account(nickname, accountType, accountId);
	}

	public boolean isMyself(final String accountId) {
		return this.accountId.equals(accountId);
	}
}
