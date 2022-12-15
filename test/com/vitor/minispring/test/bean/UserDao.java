package com.vitor.minispring.test.bean;

import java.util.HashMap;
import java.util.Map;

import com.vitor.minispring.context.annotation.Component;

@Component
public class UserDao {
	private static Map<String, String> hashMap = new HashMap<>();

	static {
		hashMap.put("10001", "Hello World");
	}

	public String queryUserName(String uId) {
		return hashMap.get(uId);
	}
}
