package com.vitor.minispring.beans.factory.config;

import com.vitor.minispring.beans.BeansException;

public interface BeanPostProcessor {

	Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

	Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
