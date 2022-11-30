package com.vitor.minispring.beans.factory.support;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.BeanFactory;
import com.vitor.minispring.beans.factory.config.BeanDefinition;
import com.vitor.minispring.beans.factory.config.DefaultSingletonBeanRegistry;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
	@Override
	public Object getBean(String beanName) throws BeansException {
		Object bean = getSingleton(beanName);
		if (bean != null) {
			return bean;
		}

		return createBean(beanName, getBeanDefinition(beanName));
	}

	protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
}
