package com.jc.callbustask.config.auth.resolver;

import static com.jc.callbustask.config.AuthWebConfig.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.jc.callbustask.config.auth.annotation.AccountId;

@Component
public class AccountArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(AccountId.class) != null
			&& parameter.getParameterType().equals(String.class);

	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		String authentication = request.getHeader(AUTHENTICATION_HEADER);
		return authentication == null ? DEFAULT_ACCOUNT_ID : authentication.split(REGEX)[ACCOUNT_ID_NUMBER];
	}
}
