package com.jc.callbustask.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jc.callbustask.config.auth.interceptor.AuthenticationInterceptor;
import com.jc.callbustask.config.auth.resolver.AccountArgumentResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class AuthWebConfig implements WebMvcConfigurer {
	public static final String AUTHENTICATION_HEADER = "Authentication";
	public static final String REGEX = " ";
	public static final int ACCOUNT_TYPE_NUMBER = 0;
	public static final int ACCOUNT_ID_NUMBER = 1;
	public static final String DEFAULT_ACCOUNT_ID = "Guest";

	private final AuthenticationInterceptor authenticationInterceptor;
	private final AccountArgumentResolver accountArgumentResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(accountArgumentResolver);
	}
}
