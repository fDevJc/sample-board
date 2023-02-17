package com.jc.callbustask.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jc.callbustask.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByAccountId(String accountId);
}
