package com.vitor.minispring.context.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.BeanFactory;
import com.vitor.minispring.beans.factory.BeanFactoryAware;
import com.vitor.minispring.context.ApplicationEvent;
import com.vitor.minispring.context.ApplicationListener;
import com.vitor.minispring.utils.ClassUtils;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
	public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();
	private BeanFactory beanFactory;

	@SuppressWarnings("unchecked")
	@Override
	public void addApplicationListener(ApplicationListener<?> listener) {
		applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
	}

	@Override
	public void removeApplicationListener(ApplicationListener<?> listener) {
		applicationListeners.remove(listener);
	}

	@Override
	public final void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@SuppressWarnings("rawtypes")
	protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
		LinkedList<ApplicationListener> allListeners = new LinkedList<>();
		for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
			if (supportsEvent(listener, event)) {
				allListeners.add(listener);
			}
		}
		return allListeners;
	}

	@SuppressWarnings("rawtypes")
	protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
		Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();
		Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass()
				: listenerClass;
		Type genericInterface = targetClass.getGenericInterfaces()[0];
		Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
		String className = actualTypeArgument.getTypeName();
		Class<?> eventClassName;
		try {
			eventClassName = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new BeansException("wrong event class name: " + className);
		}
		return eventClassName.isAssignableFrom(event.getClass());
	}

}
