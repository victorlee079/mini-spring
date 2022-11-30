package com.vitor.minispring.beans.factory.support;

import java.lang.reflect.Constructor;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.config.BeanDefinition;

public interface InstantiationStrategy {
	Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args)
			throws BeansException;
}
