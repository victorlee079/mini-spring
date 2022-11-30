package com.vitor.minispring.test.bean;

public class UserService {
	private String uid;
	private UserDao userDao;

	public void queryUserInfo() {
		System.out.println("Query User Info: " + userDao.queryUserName(uid));
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
