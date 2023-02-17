package com.jc.callbustask.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.jc.callbustask.domain.enums.AccountType;

@Entity
public class Account extends BaseEntity {
	private String nickname;

	@Enumerated(value = EnumType.STRING)
	private AccountType accountType;

	@Column(unique = true)
	private String accountId;

	private String quit;

	public boolean isMyself(String accountId) {
		return this.accountId.equals(accountId);
	}
}
