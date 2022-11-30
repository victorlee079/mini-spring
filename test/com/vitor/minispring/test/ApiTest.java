package com.vitor.minispring.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.vitor.minispring.beans.factory.config.BeanDefinition;
import com.vitor.minispring.beans.factory.support.DefaultListableBeanFactory;
import com.vitor.minispring.test.bean.UserService;

public class ApiTest {
	@Test
	public void test_BeanFactory() {
		String beanName = "userService";
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
		beanFactory.registerBeanDefinition(beanName, beanDefinition);
		UserService userService = (UserService) beanFactory.getBean(beanName);
		userService.queryUserInfo();
		assertEquals(userService, beanFactory.getSingleton(beanName));
	}
}
