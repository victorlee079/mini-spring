package com.vitor.minispring.beans.factory.support;

import java.lang.reflect.Constructor;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.PropertyValue;
import com.vitor.minispring.beans.PropertyValues;
import com.vitor.minispring.beans.factory.config.BeanDefinition;
import com.vitor.minispring.beans.factory.config.BeanPostProcessor;
import com.vitor.minispring.beans.factory.config.BeanReference;

import cn.hutool.core.bean.BeanUtil;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
		implements AutowireCapableBeanFactory {
	private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
		Object bean = null;
		try {
			bean = createBeanInstance(beanDefinition, beanName, args);
			applyPropertyValues(beanName, bean, beanDefinition);
			bean = initializeBean(beanName, bean, beanDefinition);
		} catch (Exception e) {
			throw new BeansException("Instantiation of bean failed", e);
		}

		addSingleton(beanName, bean);
		return bean;
	}

	private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
		Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
		invokeInitMethods(beanName, wrappedBean, beanDefinition);
		wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
		return wrappedBean;
	}

	private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {

	}

	protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
		try {
			PropertyValues propertyValues = beanDefinition.getPropertyValues();
			for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
				String name = propertyValue.getName();
				Object value = propertyValue.getValue();

				if (value instanceof BeanReference) {
					BeanReference beanReference = (BeanReference) value;
					value = getBean(beanReference.getBeanName());
				}

				// From hutool
				BeanUtil.setFieldValue(bean, name, value);
			}
		} catch (Exception e) {
			throw new BeansException("Error setting property values: " + beanName);
		}
	}

	protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
		Constructor<?> constructorToUse = null;
		Class<?> beanClass = beanDefinition.getBeanClass();
		Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
		for (Constructor<?> ctor : declaredConstructors) {
			// Should also compare the types
			if (null != args && ctor.getParameterTypes().length == args.length) {
				constructorToUse = ctor;
				break;
			}
		}
		return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return instantiationStrategy;
	}

	public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
		this.instantiationStrategy = instantiationStrategy;
	}

	@Override
	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
			throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessBeforeInitialization(result, beanName);
			if (null == current)
				return result;
			result = current;
		}
		return result;
	}

	@Override
	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
			throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessAfterInitialization(result, beanName);
			if (null == current)
				return result;
			result = current;
		}
		return result;
	}
}
