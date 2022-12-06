package com.vitor.minispring.context.support;

import java.util.Map;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.ConfigurableListableBeanFactory;
import com.vitor.minispring.beans.factory.config.BeanFactoryPostProcessor;
import com.vitor.minispring.beans.factory.config.BeanPostProcessor;
import com.vitor.minispring.context.ConfigurableApplicationContext;
import com.vitor.minispring.core.io.DefaultResourceLoader;

/**
 * 
 * Extend DefaultResourceLoader to handle spring.xml (loadBeanDefinitions)
 * 
 *
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader
		implements ConfigurableApplicationContext {

	@Override
	public void refresh() throws BeansException {
		refreshBeanFactory();

		ConfigurableListableBeanFactory beanFactory = getBeanFactory();

		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

		invokeBeanFactoryPostProcessors(beanFactory);

		registerBeanPostProcessors(beanFactory);

		beanFactory.preInstantiateSingletons();
	}

	protected abstract void refreshBeanFactory() throws BeansException;

	protected abstract ConfigurableListableBeanFactory getBeanFactory();

	private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory
				.getBeansOfType(BeanFactoryPostProcessor.class);
		for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
		for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
			beanFactory.addBeanPostProcessor(beanPostProcessor);
		}
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return getBeanFactory().getBeansOfType(type);
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return getBeanFactory().getBeanDefinitionNames();
	}

	@Override
	public Object getBean(String name) throws BeansException {
		return getBeanFactory().getBean(name);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return getBeanFactory().getBean(name, args);
	}

	@Override
	public <T> T getBean(String name, Class<T> type) throws BeansException {
		return getBeanFactory().getBean(name, type);
	}

	@Override
	public void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
	}

	@Override
	public void close() {
		getBeanFactory().destroySingletons();
	}
}
