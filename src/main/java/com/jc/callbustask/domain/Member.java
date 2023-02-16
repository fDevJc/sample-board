package com.jc.callbustask.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.jc.callbustask.domain.enums.AccountType;

@Entity
public class Member extends BaseEntity {
	private String nickname;

	@Enumerated(value = EnumType.STRING)
	private AccountType accountType;

	private String accountId;

	private String quit;

}
