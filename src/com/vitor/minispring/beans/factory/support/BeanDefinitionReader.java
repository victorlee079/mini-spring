package com.vitor.minispring.beans.factory.support;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.core.io.Resource;
import com.vitor.minispring.core.io.ResourceLoader;

public interface BeanDefinitionReader {
	
	BeanDefinitionRegistry getRegistry();

	ResourceLoader getResourceLoader();

	// The above methods are used to support the implementation of below methods
	
	void loadBeanDefinition(Resource resource) throws BeansException;

	void loadBeanDefinition(Resource... resources) throws BeansException;

	void loadBeanDefinition(String location) throws BeansException;

}
