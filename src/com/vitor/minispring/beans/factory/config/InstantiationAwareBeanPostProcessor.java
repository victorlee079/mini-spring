package com.vitor.minispring.beans.factory.config;

import com.vitor.minispring.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
	Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
