package com.vitor.minispring.test.bean;

import java.util.HashMap;
import java.util.Map;

import com.vitor.minispring.beans.factory.FactoryBean;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

public class ProxyBeanFactory implements FactoryBean<IUserDao> {

	@Override
	public IUserDao getObject() throws Exception {
		InvocationHandler handler = (proxt, method, args) -> {
			Map<String, String> hashMap = new HashMap<>();
			hashMap.put("10001", "Aiden");
			hashMap.put("10002", "Marcus");
			hashMap.put("10003", "Evelyn");
			return "Proxied " + method.getName() + ":" + hashMap.get(args[0].toString());
		};
		return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class[] { IUserDao.class }, handler);
	}

	@Override
	public Class<?> getObjectType() {
		return IUserDao.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
