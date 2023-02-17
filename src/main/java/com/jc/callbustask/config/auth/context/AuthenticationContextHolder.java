package com.jc.callbustask.config.auth.context;

public class AuthenticationContextHolder {
	public static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();
}
