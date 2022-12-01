package com.vitor.minispring.beans.factory;

import java.util.Map;

import com.vitor.minispring.beans.BeansException;

public interface ListableBeanFactory extends BeanFactory {
	<T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

	String[] getBeanDefinitionNames();
}
