package com.vitor.minispring.context.support;

import com.vitor.minispring.beans.factory.support.DefaultListableBeanFactory;
import com.vitor.minispring.beans.factory.support.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		String[] configLocations = getConfigLocations();
		if (null != configLocations) {
			beanDefinitionReader.loadBeanDefinitions(configLocations);
		}
	}

	protected abstract String[] getConfigLocations();
}
