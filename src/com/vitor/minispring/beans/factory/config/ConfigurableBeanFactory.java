package com.vitor.minispring.beans.factory.config;

import com.vitor.minispring.beans.factory.HierarchicalBeanFactory;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

	String SCOPE_SINGLETON = "singleton";

	String SCOPE_PROTOTYPE = "prototype";

	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
