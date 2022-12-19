package com.vitor.minispring.test;

import org.junit.Test;

import com.vitor.minispring.context.support.ClassPathXmlApplicationContext;
import com.vitor.minispring.test.bean.Husband;
import com.vitor.minispring.test.bean.IUserService;
import com.vitor.minispring.test.bean.Wife;

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

	@Test
	public void test_cycle() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring-cycle.xml");
		Husband husband = applicationContext.getBean("husband", Husband.class);
		Wife wife = applicationContext.getBean("wife", Wife.class);
		System.out.println("老公的媳妇：" + husband.queryWife());
		System.out.println("媳妇的老公：" + wife.queryHusband());
	}
}
