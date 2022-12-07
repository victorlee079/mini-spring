package com.vitor.minispring.test.bean;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.BeanClassLoaderAware;
import com.vitor.minispring.beans.factory.BeanFactory;
import com.vitor.minispring.beans.factory.BeanFactoryAware;
import com.vitor.minispring.beans.factory.BeanNameAware;
import com.vitor.minispring.context.ApplicationContext;
import com.vitor.minispring.context.ApplicationContextAware;

public class UserService implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {
	private ApplicationContext applicationContext;
	private BeanFactory beanFactory;

	private String uId;
	private IUserDao userDao;
	private String company;
	private String location;

	public String queryUserInfo() {
		return userDao.queryUserName(uId) + "," + company + "," + location;
	}

	public String getUId() {
		return uId;
	}

	public void setUId(String uId) {
		this.uId = uId;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("Bean Name is:" + name);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("ClassLoader:" + classLoader);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}
}
