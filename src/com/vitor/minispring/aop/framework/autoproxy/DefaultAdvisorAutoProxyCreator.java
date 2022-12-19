package com.vitor.minispring.aop.framework.autoproxy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import com.vitor.minispring.aop.AdvisedSupport;
import com.vitor.minispring.aop.Advisor;
import com.vitor.minispring.aop.ClassFilter;
import com.vitor.minispring.aop.Pointcut;
import com.vitor.minispring.aop.TargetSource;
import com.vitor.minispring.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.vitor.minispring.aop.framework.ProxyFactory;
import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.PropertyValues;
import com.vitor.minispring.beans.factory.BeanFactory;
import com.vitor.minispring.beans.factory.BeanFactoryAware;
import com.vitor.minispring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.vitor.minispring.beans.factory.support.DefaultListableBeanFactory;

public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;

	private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<>());

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (!earlyProxyReferences.contains(beanName)) {
			return wrapIfNecessary(bean, beanName);
		}

		return bean;
	}

	protected Object wrapIfNecessary(Object bean, String beanName) {
		if (isInfrastructureClass(bean.getClass()))
			return bean;

		Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory
				.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

		for (AspectJExpressionPointcutAdvisor advisor : advisors) {
			ClassFilter classFilter = advisor.getPointcut().getClassFilter();
			if (!classFilter.matches(bean.getClass()))
				continue;

			AdvisedSupport advisedSupport = new AdvisedSupport();

			TargetSource targetSource = new TargetSource(bean);
			advisedSupport.setTargetSource(targetSource);
			advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
			advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
			advisedSupport.setProxyTargetClass(true);

			return new ProxyFactory(advisedSupport).getProxy();
		}

		return bean;
	}

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		return null;
	}

	private boolean isInfrastructureClass(Class<?> beanClass) {
		return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass)
				|| Advisor.class.isAssignableFrom(beanClass);
	}

	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName)
			throws BeansException {
		return pvs;
	}

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		return true;
	}

	@Override
	public Object getEarlyBeanReference(Object bean, String beanName) {
		earlyProxyReferences.add(beanName);
		return wrapIfNecessary(bean, beanName);
	}
}
