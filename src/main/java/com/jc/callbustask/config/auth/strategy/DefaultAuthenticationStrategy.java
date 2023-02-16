package com.jc.callbustask.config.auth.strategy;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.jc.callbustask.config.auth.annotation.Authority;

@Component
public class DefaultAuthenticationStrategy implements AuthenticationStrategy {

	private static final String AUTHENTICATION_HEADER = "Authentication";
	private static final String REGEX = " ";
	private static final int HEADER_NUMBER = 0;

	@Override
	public Boolean isCertified(final HttpServletRequest request, final Authority authority) {
		String upperCasePrefixValue = request.getHeader(AUTHENTICATION_HEADER).split(REGEX)[HEADER_NUMBER].toUpperCase();
		return Arrays.stream(authority.target())
			.anyMatch(accountType -> accountType.name().equals(upperCasePrefixValue));
	}
}
