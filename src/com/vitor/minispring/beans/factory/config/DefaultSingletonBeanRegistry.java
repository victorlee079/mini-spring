package com.vitor.minispring.beans.factory.config;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	private Map<String, Object> singletonObjects = new HashMap<>();

	@Override
	public Object getSingleton(String beanName) {
		return this.singletonObjects.get(beanName);
	}
	
	protected void addSingleton(String beanName, Object singletonObject) {
		this.singletonObjects.put(beanName, singletonObject);
	}
}
