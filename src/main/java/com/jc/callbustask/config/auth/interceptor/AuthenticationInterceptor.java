package com.jc.callbustask.config.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jc.callbustask.config.auth.annotation.Authority;
import com.jc.callbustask.config.auth.exception.InvalidMemberTypeAccessException;
import com.jc.callbustask.config.auth.strategy.AuthenticationStrategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
	private final AuthenticationStrategy authenticationStrategy;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		Authority authority = ((HandlerMethod)handler).getMethodAnnotation(Authority.class);
		if (authority == null || authenticationStrategy.isCertified(request, authority)) {
			return true;
		}

		throw new InvalidMemberTypeAccessException();

	}
}
