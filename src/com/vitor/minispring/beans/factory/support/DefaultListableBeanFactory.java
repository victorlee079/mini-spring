package com.vitor.minispring.beans.factory.support;

import java.util.HashMap;
import java.util.Map;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.config.BeanDefinition;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {
	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

	@Override
	protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
		BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
		if (beanDefinition == null) {
			throw new BeansException("No bean named '" + beanName + "'");
		}
		return beanDefinition;
	}

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}

}
