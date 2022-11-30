package com.vitor.minispring.beans.factory.support;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
		Object bean = null;
		try {
			bean = beanDefinition.getBeanClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new BeansException("Instantiation of bean failed", e);
		}

		addSingleton(beanName, bean);
		return bean;
	}
}
