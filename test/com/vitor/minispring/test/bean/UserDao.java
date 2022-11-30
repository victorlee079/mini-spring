package com.vitor.minispring.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
	private static Map<String, String> hashMap = new HashMap<>();

	static {
		hashMap.put("10001", "Aiden");
		hashMap.put("10002", "Marcus");
		hashMap.put("10003", "Evelyn");
	}

	public String queryUserName(String uid) {
		return hashMap.get(uid);
	}
}
