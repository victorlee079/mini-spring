package com.vitor.minispring.beans.factory.support;

import java.util.ArrayList;
import java.util.List;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.FactoryBean;
import com.vitor.minispring.beans.factory.config.BeanDefinition;
import com.vitor.minispring.beans.factory.config.BeanPostProcessor;
import com.vitor.minispring.beans.factory.config.ConfigurableBeanFactory;
import com.vitor.minispring.core.convert.ConversionService;
import com.vitor.minispring.utils.ClassUtils;
import com.vitor.minispring.utils.StringValueResolver;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

	private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

	private ConversionService conversionService;

	@Override
	public Object getBean(String beanName) throws BeansException {
		return doGetBean(beanName, null);
	}

	@Override
	public Object getBean(String beanName, Object... args) throws BeansException {
		return doGetBean(beanName, args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return (T) getBean(name);
	}

	@SuppressWarnings("unchecked")
	protected <T> T doGetBean(String beanName, Object[] args) {
		Object sharedInstance = getSingleton(beanName);
		if (sharedInstance != null) {
			return (T) getObjectForBeanInstance(sharedInstance, beanName);
		}

		Object bean = (T) createBean(beanName, getBeanDefinition(beanName), args);
		return (T) getObjectForBeanInstance(bean, beanName);
	}

	private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
		if (!(beanInstance instanceof FactoryBean)) {
			return beanInstance;
		}

		Object object = getCachedObjectForFactoryBean(beanName);
		if (object == null) {
			FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
			object = getObjectFromFactoryBean(factoryBean, beanName);
		}

		return object;
	}

	protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args)
			throws BeansException;

	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		this.beanPostProcessors.remove(beanPostProcessor);
		this.beanPostProcessors.add(beanPostProcessor);
	}

	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
	}

	public ClassLoader getBeanClassLoader() {
		return beanClassLoader;
	}

	@Override
	public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
		this.embeddedValueResolvers.add(valueResolver);
	}

	@Override
	public String resolveEmbeddedValue(String value) {
		String result = value;
		for (StringValueResolver resolver : this.embeddedValueResolvers) {
			result = resolver.resolveStringValue(value);
		}
		return result;
	}

	@Override
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Override
	public ConversionService getConversionService() {
		return conversionService;
	}

	@Override
	public boolean containsBean(String name) {
		return containsBeanDefinition(name);
	}

	protected abstract boolean containsBeanDefinition(String beanName);
}
