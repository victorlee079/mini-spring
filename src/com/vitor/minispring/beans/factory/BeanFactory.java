package com.vitor.minispring.beans.factory;

import com.vitor.minispring.beans.BeansException;

public interface BeanFactory {

	Object getBean(String beanName) throws BeansException;

	Object getBean(String beanName, Object... args) throws BeansException;

	<T> T getBean(String name, Class<T> requiredType) throws BeansException;

	<T> T getBean(Class<T> requiredType) throws BeansException;
}
