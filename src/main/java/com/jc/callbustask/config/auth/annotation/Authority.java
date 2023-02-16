package com.jc.callbustask.config.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jc.callbustask.domain.enums.AccountType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {
	AccountType[] target() default {AccountType.LESSEE, AccountType.LESSOR, AccountType.REALTOR};
}
