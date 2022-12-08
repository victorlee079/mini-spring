package com.vitor.minispring.beans.factory.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.DisposableBean;
import com.vitor.minispring.beans.factory.config.SingletonBeanRegistry;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	protected static final Object NULL_OBJECT = new Object();
	private Map<String, Object> singletonObjects = new HashMap<>();
	private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

	@Override
	public Object getSingleton(String beanName) {
		return this.singletonObjects.get(beanName);
	}

	protected void addSingleton(String beanName, Object singletonObject) {
		this.singletonObjects.put(beanName, singletonObject);
	}

	public void registerDisposableBean(String beanName, DisposableBean bean) {
		this.disposableBeans.put(beanName, bean);
	}

	public void destroySingletons() {
		Set<String> keySet = disposableBeans.keySet();
		Object[] disposableBeanNames = keySet.toArray();

		for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
			Object beanName = disposableBeanNames[i];
			DisposableBean disposableBean = disposableBeans.remove(beanName);
			try {
				disposableBean.destroy();
			} catch (Exception e) {
				throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
			}
		}
	}

	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		singletonObjects.put(beanName, singletonObject);
	}
}
