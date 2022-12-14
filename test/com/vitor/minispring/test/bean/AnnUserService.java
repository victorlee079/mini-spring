package com.vitor.minispring.test.bean;

import java.util.Random;

import com.vitor.minispring.context.annotation.Component;

@Component("userService")
public class AnnUserService implements IUserService {

	private String token;

	@Override
	public String queryUserInfo() {
		try {
			Thread.sleep(new Random(1).nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "TEST,123456,HK";
	}

	@Override
	public String register(String userName) {
		try {
			Thread.sleep(new Random(1).nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Register:" + userName + " done!";
	}

	@Override
	public String toString() {
		return "UserService#token={" + token + "}";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
