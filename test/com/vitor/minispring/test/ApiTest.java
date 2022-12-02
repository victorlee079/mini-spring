package com.vitor.minispring.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.vitor.minispring.beans.PropertyValue;
import com.vitor.minispring.beans.PropertyValues;
import com.vitor.minispring.beans.factory.config.BeanDefinition;
import com.vitor.minispring.beans.factory.config.BeanReference;
import com.vitor.minispring.beans.factory.support.DefaultListableBeanFactory;
import com.vitor.minispring.beans.factory.support.XmlBeanDefinitionReader;
import com.vitor.minispring.context.support.ClassPathXmlApplicationContext;
import com.vitor.minispring.core.io.DefaultResourceLoader;
import com.vitor.minispring.core.io.Resource;
import com.vitor.minispring.core.io.ResourceLoader;
import com.vitor.minispring.test.bean.UserDao;
import com.vitor.minispring.test.bean.UserService;

import cn.hutool.core.io.IoUtil;

public class ApiTest {
	private ResourceLoader resourceLoader;

	@Before
	public void init() {
		resourceLoader = new DefaultResourceLoader();
	}

	@Test
	public void test_BeanFactory() {
		String userDaoName = "userDao";
		String userServiceName = "userService";

		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		beanFactory.registerBeanDefinition(userDaoName, new BeanDefinition(UserDao.class));

		PropertyValues propertyValues = new PropertyValues();
		propertyValues.addPropertyValue(new PropertyValue("uId", "10002"));
		propertyValues.addPropertyValue(new PropertyValue(userDaoName, new BeanReference(userDaoName)));

		beanFactory.registerBeanDefinition(userServiceName, new BeanDefinition(UserService.class, propertyValues));

		UserService userService = (UserService) beanFactory.getBean(userServiceName);
		userService.queryUserInfo();
	}

	@Test
	public void test_classpath() throws IOException {
		Resource resource = resourceLoader.getResource("classpath:important.properties");
		String content = IoUtil.readUtf8(resource.getInputStream());
		System.out.println(content);
	}

	@Test
	public void test_file() throws IOException {
		Resource resource = resourceLoader.getResource("resources/important.properties");
		String content = IoUtil.readUtf8(resource.getInputStream());
		System.out.println(content);
	}

	@Test
	public void test_nppxml() throws IOException {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_npp.xml");

		UserService userService = applicationContext.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println(result);
	}

	@Test
	public void test_xml() throws IOException {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

		UserService userService = applicationContext.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println(result);
	}
}
