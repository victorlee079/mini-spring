package com.vitor.minispring.beans.factory.config;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
	/**
	 * Allows for custom modification of an application context's bean definitions,adapting the bean property values of the context's underlying bean factory.
	 * 
	 * @param beanFactory
	 * @throws BeansException
	 */
	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
