package com.vitor.minispring.context.support;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.config.BeanPostProcessor;
import com.vitor.minispring.context.ApplicationContext;
import com.vitor.minispring.context.ApplicationContextAware;

public class ApplicationContextAwareProcessor implements BeanPostProcessor {

	private final ApplicationContext applicationContext;

	public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof ApplicationContextAware) {
			((ApplicationContextAware) bean).setApplicationContext(applicationContext);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
