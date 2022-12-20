package com.vitor.minispring.context.support;

import java.util.Collection;
import java.util.Map;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.ConfigurableListableBeanFactory;
import com.vitor.minispring.beans.factory.config.BeanFactoryPostProcessor;
import com.vitor.minispring.beans.factory.config.BeanPostProcessor;
import com.vitor.minispring.context.ApplicationEvent;
import com.vitor.minispring.context.ApplicationListener;
import com.vitor.minispring.context.ConfigurableApplicationContext;
import com.vitor.minispring.context.event.ApplicationEventMulticaster;
import com.vitor.minispring.context.event.ContextClosedEvent;
import com.vitor.minispring.context.event.ContextRefreshedEvent;
import com.vitor.minispring.context.event.SimpleApplicationEventMulticaster;
import com.vitor.minispring.core.convert.ConversionService;
import com.vitor.minispring.core.io.DefaultResourceLoader;

/**
 * 
 * Extend DefaultResourceLoader to handle spring.xml (loadBeanDefinitions)
 * 
 *
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader
		implements ConfigurableApplicationContext {

	public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

	private ApplicationEventMulticaster applicationEventMulticaster;

	@Override
	public void refresh() throws BeansException {
		refreshBeanFactory();

		ConfigurableListableBeanFactory beanFactory = getBeanFactory();

		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

		invokeBeanFactoryPostProcessors(beanFactory);

		registerBeanPostProcessors(beanFactory);

		initApplicationEventMulticaster();

		registerListeners();

		finishBeanFactoryInitialization(beanFactory);

		finishRefresh();
	}

	private void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
		if (beanFactory.containsBean("conversionService")) {
			Object conversionService = beanFactory.getBean("conversionService");
			if (conversionService instanceof ConversionService) {
				beanFactory.setConversionService((ConversionService) conversionService);
			}
		}

		beanFactory.preInstantiateSingletons();
	}

	private void finishRefresh() {
		publishEvent(new ContextRefreshedEvent(this));
	}

	@Override
	public void publishEvent(ApplicationEvent event) {
		applicationEventMulticaster.multicastEvent(event);
	}

	@SuppressWarnings("rawtypes")
	private void registerListeners() {
		Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
		for (ApplicationListener listener : applicationListeners) {
			applicationEventMulticaster.addApplicationListener(listener);
		}
	}

	private void initApplicationEventMulticaster() {
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
		beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
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
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		return getBeanFactory().getBean(requiredType);
	}

	@Override
	public void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
	}

	@Override
	public void close() {
		publishEvent(new ContextClosedEvent(this));
		getBeanFactory().destroySingletons();
	}

	@Override
	public boolean containsBean(String name) {
		return getBeanFactory().containsBean(name);
	}
}
