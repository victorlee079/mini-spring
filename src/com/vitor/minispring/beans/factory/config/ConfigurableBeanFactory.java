package com.vitor.minispring.beans.factory.config;

import com.vitor.minispring.beans.factory.HierarchicalBeanFactory;
import com.vitor.minispring.core.convert.ConversionService;
import com.vitor.minispring.utils.StringValueResolver;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

	String SCOPE_SINGLETON = "singleton";

	String SCOPE_PROTOTYPE = "prototype";

	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

	void destroySingletons();

	void addEmbeddedValueResolver(StringValueResolver valueResolver);

	// Resolve the given embedded value (e.g. an annotation attribute)
	String resolveEmbeddedValue(String value);

	ConversionService getConversionService();

	void setConversionService(ConversionService conversionService);
}
