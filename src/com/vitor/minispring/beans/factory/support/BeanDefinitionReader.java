package com.vitor.minispring.beans.factory.support;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.core.io.Resource;
import com.vitor.minispring.core.io.ResourceLoader;

public interface BeanDefinitionReader {

	BeanDefinitionRegistry getRegistry();

	ResourceLoader getResourceLoader();

	// The above methods are used to support the implementation of below methods

	void loadBeanDefinitions(Resource resource) throws BeansException;

	void loadBeanDefinitions(Resource... resources) throws BeansException;

	void loadBeanDefinitions(String location) throws BeansException;

	void loadBeanDefinitions(String... locations) throws BeansException;
}
