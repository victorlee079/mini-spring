package com.vitor.minispring.beans.factory.support;

import java.lang.reflect.Constructor;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.config.BeanDefinition;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

	@Override
	public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args)
			throws BeansException {
		Enhancer e = new Enhancer();
		e.setSuperclass(beanDefinition.getBeanClass());
		// No operations for simplicity
		e.setCallback(new NoOp() {
			@Override
			public int hashCode() {
				return super.hashCode();
			}
		});
		if (null == ctor) {
			return e.create();
		}
		return e.create(ctor.getParameterTypes(), args);
	}

}
