package com.vitor.minispring.test.bean;

import java.util.Random;

import com.vitor.minispring.beans.factory.annotation.Autowired;
import com.vitor.minispring.beans.factory.annotation.Value;
import com.vitor.minispring.context.annotation.Component;

@Component
public class UserService implements IUserService {
	@Value("${token}")
	private String token;

	@Autowired
	private UserDao userDao;

	public String queryUserInfo() {
		try {
			Thread.sleep(new Random(1).nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return userDao.queryUserName("10001") + "'" + token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public String register(String userName) {
		return userName;
	}
}
