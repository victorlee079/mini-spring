package com.vitor.minispring.aop;

import org.aopalliance.aop.Advice;

public interface Advisor {
	// Return the advice part of this aspect
	Advice getAdvice();
}
