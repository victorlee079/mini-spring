package com.vitor.minispring.beans.factory.support;

import com.vitor.minispring.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
