package com.vitor.minispring.beans.factory;

import java.io.IOException;
import java.util.Properties;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.PropertyValue;
import com.vitor.minispring.beans.PropertyValues;
import com.vitor.minispring.beans.factory.config.BeanDefinition;
import com.vitor.minispring.beans.factory.config.BeanFactoryPostProcessor;
import com.vitor.minispring.core.io.DefaultResourceLoader;
import com.vitor.minispring.core.io.Resource;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

	public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

	public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

	private String location;

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		try {
			DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource resource = resourceLoader.getResource(location);
			Properties properties = new Properties();
			properties.load(resource.getInputStream());

			String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
			for (String beanName : beanDefinitionNames) {
				BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
				PropertyValues propertyValues = beanDefinition.getPropertyValues();
				for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
					Object value = propertyValue.getValue();
					if (!(value instanceof String)) {
						continue;
					}
					String strVal = (String) value;
					StringBuilder buffer = new StringBuilder(strVal);
					int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
					int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
					if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
						String propKey = strVal.substring(startIdx + 2, stopIdx);
						String propVal = properties.getProperty(propKey);
						buffer.replace(startIdx, stopIdx + 1, propVal);
						propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buffer.toString()));
					}
				}
			}
		} catch (IOException e) {
			throw new BeansException("Could not load properties", e);
		}
	}

}
