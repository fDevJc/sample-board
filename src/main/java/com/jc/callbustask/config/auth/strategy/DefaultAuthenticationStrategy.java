package com.jc.callbustask.config.auth.strategy;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.jc.callbustask.config.auth.annotation.Authority;
import com.jc.callbustask.config.auth.context.AuthenticationContextHolder;

@Component
public class DefaultAuthenticationStrategy implements AuthenticationStrategy {

	private static final String AUTHENTICATION_HEADER = "Authentication";
	private static final String REGEX = " ";
	private static final int ACCOUNT_TYPE_NUMBER = 0;
	private static final int ACCOUNT_ID_NUMBER = 1;

	@Override
	public Boolean isCertified(final HttpServletRequest request, final Authority authority) {
		String upperCasePrefixValue = request.getHeader(AUTHENTICATION_HEADER).split(REGEX)[ACCOUNT_TYPE_NUMBER].toUpperCase();
		return Arrays.stream(authority.target())
			.anyMatch(accountType -> accountType.name().equals(upperCasePrefixValue));
	}

	@Override
	public void setContext(HttpServletRequest request) {
		AuthenticationContextHolder.CONTEXT.set(request.getHeader(AUTHENTICATION_HEADER).split(REGEX)[ACCOUNT_ID_NUMBER]);
	}

	@Override
	public void removeContext() {
		AuthenticationContextHolder.CONTEXT.remove();
	}
}
