package com.vitor.minispring.test.bean;

import com.vitor.minispring.beans.factory.FactoryBean;

import net.sf.cglib.proxy.Proxy;

public class HusbandMother implements FactoryBean<IMother> {

	@Override
	public IMother getObject() throws Exception {
		return (IMother) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class[] { IMother.class }, (proxy, method, args) -> "Proxied!" + method.getName());
	}

	@Override
	public Class<?> getObjectType() {
		return IMother.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
