package com.vitor.minispring.aop.framework.autoproxy;

import java.util.Collection;

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
		return bean;
	}

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		if (isInfrastructureClass(beanClass)) {
			return null;
		}

		Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory
				.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

		for (AspectJExpressionPointcutAdvisor advisor : advisors) {
			ClassFilter classFilter = advisor.getPointcut().getClassFilter();
			if (!classFilter.matches(beanClass)) {
				continue;
			}

			AdvisedSupport advisedSupport = new AdvisedSupport();
			TargetSource targetSource = null;
			try {
				targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}

			advisedSupport.setTargetSource(targetSource);
			advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
			advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
			advisedSupport.setProxyTargetClass(false);

			return new ProxyFactory(advisedSupport).getProxy();
		}

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

}
