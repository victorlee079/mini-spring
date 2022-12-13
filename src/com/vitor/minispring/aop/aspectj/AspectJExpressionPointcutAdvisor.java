package com.vitor.minispring.aop.aspectj;

import org.aopalliance.aop.Advice;

import com.vitor.minispring.aop.Pointcut;
import com.vitor.minispring.aop.PointcutAdvisor;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

	private AspectJExpressionPointcut pointcut;

	private Advice advice;

	private String expression;

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}

	@Override
	public Advice getAdvice() {
		return advice;
	}

	@Override
	public Pointcut getPointcut() {
		if (null == pointcut) {
			pointcut = new AspectJExpressionPointcut(expression);
		}
		return pointcut;
	}

}
