package com.vitor.minispring.test;

import org.junit.Test;

import com.vitor.minispring.context.support.ClassPathXmlApplicationContext;
import com.vitor.minispring.test.bean.IUserService;

public class ApiTest {

	@Test
	public void test_scan() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		IUserService userService = applicationContext.getBean("userService", IUserService.class);
		System.out.println("Test Result: " + userService.queryUserInfo());
	}

	@Test
	public void test_autoProxy() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring-autoproxy.xml");
		IUserService userService = applicationContext.getBean("userService", IUserService.class);
		System.out.println("Test Result: " + userService.getToken());
	}
}
