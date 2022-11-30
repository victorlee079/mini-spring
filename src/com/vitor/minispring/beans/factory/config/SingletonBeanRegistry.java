package com.vitor.minispring.beans.factory.config;

public interface SingletonBeanRegistry {
	public Object getSingleton(String beanName);
}
