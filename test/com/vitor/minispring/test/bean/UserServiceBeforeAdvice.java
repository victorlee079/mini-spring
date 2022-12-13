package com.vitor.minispring.test.bean;

import java.lang.reflect.Method;

import com.vitor.minispring.aop.MethodBeforeAdvice;

public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("Intercepted: " + method.getName());
	}

}
