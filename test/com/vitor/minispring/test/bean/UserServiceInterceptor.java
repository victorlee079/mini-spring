package com.vitor.minispring.test.bean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class UserServiceInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			return invocation.proceed();
		} finally {
			System.out.println("Monitor - Begin By AOP");
			System.out.println("Method: " + invocation.getMethod());
			System.out.println("Duration: " + (System.currentTimeMillis() - start) + "ms");
			System.out.println("Monitor - End\r\n");
		}
	}

}
