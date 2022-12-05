package com.vitor.minispring.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
	private static Map<String, String> hashMap = new HashMap<>();

	public void initDataMethod() {
		System.out.println("init-method");
		hashMap.put("10001", "Aiden");
		hashMap.put("10002", "Marcus");
		hashMap.put("10003", "Evelyn");
	}

	public void destroyDataMethod() {
		System.out.println("detroy-method");
		hashMap.clear();
	}

	public String queryUserName(String uid) {
		return hashMap.get(uid);
	}
}
