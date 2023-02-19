package com.jc.callbustask.config.auth.strategy;

import static com.jc.callbustask.config.AuthWebConfig.*;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.jc.callbustask.config.auth.annotation.Authority;
import com.jc.callbustask.config.auth.exception.InvalidMemberTypeAccessException;

@Component
public class DefaultAuthenticationStrategy implements AuthenticationStrategy {
	@Override
	public Boolean isCertified(final HttpServletRequest request, final Authority authority) {
		String header = request.getHeader(AUTHENTICATION_HEADER);
		if (header == null) {
			throw new InvalidMemberTypeAccessException();
		}
		String upperCasePrefixValue = header.split(REGEX)[ACCOUNT_TYPE_NUMBER].toUpperCase();
		return Arrays.stream(authority.target())
			.anyMatch(accountType -> accountType.name().equals(upperCasePrefixValue));
	}
}
