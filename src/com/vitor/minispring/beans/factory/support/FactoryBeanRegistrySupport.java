package com.vitor.minispring.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.FactoryBean;

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

	protected Object getCachedObjectForFactoryBean(String beanName) {
		Object object = this.factoryBeanObjectCache.get(beanName);
		return (object != NULL_OBJECT ? object : null);
	}

	protected Object getObjectFromFactoryBean(@SuppressWarnings("rawtypes") FactoryBean factory, String beanName) {
		if (factory.isSingleton()) {
			Object object = this.factoryBeanObjectCache.get(beanName);
			if (object == null) {
				object = doGetObjectFromFactoryBean(factory, beanName);
				this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
			}
			return (object != NULL_OBJECT ? object : null);
		} else {
			return doGetObjectFromFactoryBean(factory, beanName);
		}
	}

	protected Object doGetObjectFromFactoryBean(@SuppressWarnings("rawtypes") final FactoryBean factory, final String beanName) {
		try {
			return factory.getObject();
		} catch (Exception e) {
			throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
		}
	}
}
