package com.vitor.minispring.test;

import java.io.IOException;

import org.junit.Test;

import com.vitor.minispring.context.support.ClassPathXmlApplicationContext;
import com.vitor.minispring.test.bean.UserService;

public class ApiTest {
	@Test
	public void test_xml() throws IOException {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.registerShutdownHook();
		UserService userService = applicationContext.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println(result);

		System.out.println("ApplicationContext:" + userService.getApplicationContext());
		System.out.println("BeanFactory:" + userService.getBeanFactory());
	}
}
