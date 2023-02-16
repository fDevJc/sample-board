package com.jc.callbustask.config.auth.strategy;

import javax.servlet.http.HttpServletRequest;

import com.jc.callbustask.config.auth.annotation.Authority;

public interface AuthenticationStrategy {
	Boolean isCertified(HttpServletRequest request, Authority authority);
}
