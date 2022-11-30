package com.vitor.minispring.beans.factory.support;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.BeanFactory;
import com.vitor.minispring.beans.factory.config.BeanDefinition;
import com.vitor.minispring.beans.factory.config.DefaultSingletonBeanRegistry;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
	@Override
	public Object getBean(String beanName) throws BeansException {
		return doGetBean(beanName, null);
	}

	@Override
	public Object getBean(String beanName, Object... args) throws BeansException {
		return doGetBean(beanName, args);
	}

	@SuppressWarnings("unchecked")
	protected <T> T doGetBean(String beanName, Object[] args) {
		Object bean = getSingleton(beanName);
		if (bean != null) {
			return (T) bean;
		}

		return (T) createBean(beanName, getBeanDefinition(beanName), args);
	}

	protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args)
			throws BeansException;
}
