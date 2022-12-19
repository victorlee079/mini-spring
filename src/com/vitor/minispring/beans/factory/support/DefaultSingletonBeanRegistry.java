package com.vitor.minispring.beans.factory.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.DisposableBean;
import com.vitor.minispring.beans.factory.ObjectFactory;
import com.vitor.minispring.beans.factory.config.SingletonBeanRegistry;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	protected static final Object NULL_OBJECT = new Object();
	private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
	protected final Map<String, Object> earlySingletonObjects = new HashMap<>();
	protected final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();
	private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

	@Override
	public Object getSingleton(String beanName) {
		Object singletonObject = singletonObjects.get(beanName);
		if (null == singletonObject) {
			singletonObject = earlySingletonObjects.get(beanName);
			if (null == singletonObject) {
				ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
				if (singletonFactory != null) {
					singletonObject = singletonFactory.getObject();
					earlySingletonObjects.put(beanName, singletonObject);
					singletonFactories.remove(beanName);
				}
			}
		}
		return singletonObject;
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
		earlySingletonObjects.remove(beanName);
		singletonFactories.remove(beanName);
	}

	protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
		if (!this.singletonObjects.containsKey(beanName)) {
			this.singletonFactories.put(beanName, singletonFactory);
			this.earlySingletonObjects.remove(beanName);
		}
	}
}
