package com.vitor.minispring.context.support;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.ConfigurableListableBeanFactory;
import com.vitor.minispring.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

	private DefaultListableBeanFactory beanFactory;

	@Override
	protected void refreshBeanFactory() throws BeansException {
		DefaultListableBeanFactory beanFactory = createBeanFactory();
		loadBeanDefinitions(beanFactory);
		this.beanFactory = beanFactory;
	}

	protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

	private DefaultListableBeanFactory createBeanFactory() {
		return new DefaultListableBeanFactory();
	}

	@Override
	protected ConfigurableListableBeanFactory getBeanFactory() {
		return beanFactory;
	}
}
