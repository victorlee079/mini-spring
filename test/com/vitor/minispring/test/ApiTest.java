package com.vitor.minispring.test;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.vitor.minispring.aop.AdvisedSupport;
import com.vitor.minispring.aop.TargetSource;
import com.vitor.minispring.aop.aspectj.AspectJExpressionPointcut;
import com.vitor.minispring.aop.framework.Cglib2AopProxy;
import com.vitor.minispring.aop.framework.JdkDynamicAopProxy;
import com.vitor.minispring.context.support.ClassPathXmlApplicationContext;
import com.vitor.minispring.test.bean.AopUserService;
import com.vitor.minispring.test.bean.IUserService;
import com.vitor.minispring.test.bean.UserService;
import com.vitor.minispring.test.bean.UserServiceInterceptor;
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

	@Test
	public void test_aop() throws NoSuchMethodException, SecurityException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(
				"execution(* com.vitor.minispring.test.bean.UserService.*(..))");
		Class<UserService> clazz = UserService.class;
		Method method = clazz.getDeclaredMethod("queryUserInfo");
		Assert.assertTrue(pointcut.matches(clazz));
		Assert.assertTrue(pointcut.matches(method, clazz));
	}

	@Test
	public void test_dynamic() {
		IUserService userService = new AopUserService();

		AdvisedSupport advisedSupport = new AdvisedSupport();
		advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
		advisedSupport.setTargetSource(new TargetSource(userService));
		advisedSupport.setMethodMatcher(
				new AspectJExpressionPointcut("execution(* com.vitor.minispring.test.bean.IUserService.*(..))"));

		IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
		System.out.println(proxy_jdk.queryUserInfo());
		IUserService proxy_cglibk = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
		System.out.println(proxy_cglibk.register("Hello"));
	}
}
