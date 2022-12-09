package com.vitor.minispring.aop;

public interface Pointcut {
	ClassFilter getClassFilter();

	MethodMatcher getMethodMatcher();
}
