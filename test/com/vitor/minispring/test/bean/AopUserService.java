package com.vitor.minispring.test.bean;

import java.util.Random;

public class AopUserService implements IUserService {

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

}
