package com.vitor.minispring.test.common;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.config.BeanPostProcessor;
import com.vitor.minispring.test.bean.UserService;

public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if ("userService".equals(beanName)) {
			((UserService) bean).setLocation("Tokyo");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
