package com.vitor.minispring.beans.factory.support;

import java.lang.reflect.Method;

import com.vitor.minispring.beans.factory.DisposableBean;
import com.vitor.minispring.beans.factory.config.BeanDefinition;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.util.StrUtil;

public class DisposableBeanAdapter implements DisposableBean {

	private final Object bean;
	private final String beanName;
	private String destroyMethodName;

	public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
		this.bean = bean;
		this.beanName = beanName;
		this.destroyMethodName = beanDefinition.getDestroyMethodName();
	}

	@Override
	public void destroy() throws Exception {
		if (bean instanceof DisposableBean) {
			((DisposableBean) bean).destroy();
		}

		if (StrUtil.isNotEmpty(destroyMethodName)
				&& !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
			Method detroyMethod = bean.getClass().getMethod(destroyMethodName);
			if (null == detroyMethod) {
				throw new BeanException("Couldn't find a destroy method named '" + destroyMethodName
						+ "' on bean with name '" + beanName + "'");
			}
			detroyMethod.invoke(bean);
		}
	}

}
