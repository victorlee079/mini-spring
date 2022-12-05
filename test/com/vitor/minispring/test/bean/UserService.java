package com.vitor.minispring.test.bean;

import com.vitor.minispring.beans.factory.DisposableBean;
import com.vitor.minispring.beans.factory.InitializingBean;

public class UserService implements InitializingBean, DisposableBean {
	private String uId;
	private UserDao userDao;
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

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
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
	public void destroy() throws Exception {
		System.out.println("UserService.destroy");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("UserService.afterPropertiesSet");
	}
}
