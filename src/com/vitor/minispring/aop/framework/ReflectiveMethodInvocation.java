package com.vitor.minispring.aop.framework;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

public class ReflectiveMethodInvocation implements MethodInvocation {
	protected final Object target;
	protected final Method method;
	protected final Object[] arguments;

	public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
		this.target = target;
		this.method = method;
		this.arguments = arguments;
	}

	@Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return method;
	}

	@Override
	public Object getThis() {
		return target;
	}

	@Override
	public Object proceed() throws Throwable {
		return method.invoke(target, arguments);
	}

	@Override
	public Method getMethod() {
		return method;
	}
}
