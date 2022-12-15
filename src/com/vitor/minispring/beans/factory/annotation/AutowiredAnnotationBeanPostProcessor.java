package com.vitor.minispring.beans.factory.annotation;

import java.lang.reflect.Field;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.PropertyValues;
import com.vitor.minispring.beans.factory.BeanFactory;
import com.vitor.minispring.beans.factory.BeanFactoryAware;
import com.vitor.minispring.beans.factory.config.ConfigurableBeanFactory;
import com.vitor.minispring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.vitor.minispring.utils.ClassUtils;

import cn.hutool.core.bean.BeanUtil;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	private ConfigurableBeanFactory beanFactory;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return null;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return null;
	}

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		return null;
	}

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		return true;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableBeanFactory) beanFactory;
	}

	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName)
			throws BeansException {
		Class<?> clazz = bean.getClass();
		clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
		Field[] declaredFields = clazz.getDeclaredFields();

		for (Field field : declaredFields) {
			Value valueAnnotation = field.getAnnotation(Value.class);
			if (null != valueAnnotation) {
				String value = valueAnnotation.value();
				value = beanFactory.resolveEmbeddedValue(value);
				BeanUtil.setFieldValue(bean, field.getName(), value);
			}
		}

		for (Field field : declaredFields) {
			Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
			if (null != autowiredAnnotation) {
				Class<?> fieldType = field.getType();
				String dependentBeanName = null;
				Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
				Object dependentBean = null;
				if (null != qualifierAnnotation) {
					dependentBeanName = qualifierAnnotation.value();
					dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
				} else {
					dependentBean = beanFactory.getBean(fieldType);
				}
				BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
			}
		}

		return pvs;
	}

}
