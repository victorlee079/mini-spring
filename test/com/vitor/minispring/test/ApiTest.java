package com.vitor.minispring.test;

import org.junit.Test;

import com.vitor.minispring.context.support.ClassPathXmlApplicationContext;
import com.vitor.minispring.test.bean.UserService;
import com.vitor.minispring.test.event.CustomEvent;

public class ApiTest {
	@Test
	public void test_prototype() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.registerShutdownHook();

		UserService userService01 = applicationContext.getBean("userService", UserService.class);
		UserService userService02 = applicationContext.getBean("userService", UserService.class);

		System.out.println(userService01);
		System.out.println(userService02);
	}

	@Test
	public void test_factory_bean() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.registerShutdownHook();
		// 2. 调用代理方法
		UserService userService = applicationContext.getBean("userService", UserService.class);
		System.out.println("测试结果：" + userService.queryUserInfo());
	}

	@Test
	public void test_event() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring_event.xml");
		applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "Success~"));
		applicationContext.registerShutdownHook();
	}
}
