package com.vitor.minispring.test.common;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.PropertyValue;
import com.vitor.minispring.beans.factory.ConfigurableListableBeanFactory;
import com.vitor.minispring.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		beanFactory.getBeanDefinition("userService").getPropertyValues()
				.addPropertyValue(new PropertyValue("company", "LINE"));
	}

}
