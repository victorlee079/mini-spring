package com.vitor.minispring.aop;

public interface PointcutAdvisor extends Advisor {
	// Get the Pointcut that drives this advisor
	Pointcut getPointcut();
}
