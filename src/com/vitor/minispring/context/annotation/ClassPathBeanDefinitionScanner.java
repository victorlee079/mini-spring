package com.vitor.minispring.context.annotation;

import java.util.Set;

import com.vitor.minispring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.vitor.minispring.beans.factory.config.BeanDefinition;
import com.vitor.minispring.beans.factory.support.BeanDefinitionRegistry;

import cn.hutool.core.util.StrUtil;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
	private BeanDefinitionRegistry registry;

	public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}

	public void doScan(String... basePackages) {
		for (String basePackage : basePackages) {
			Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
			for (BeanDefinition beanDefinition : candidates) {
				String beanScope = resolveBeanScope(beanDefinition);
				if (StrUtil.isNotEmpty(beanScope)) {
					beanDefinition.setScope(beanScope);
				}
				registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
			}
		}

		registry.registerBeanDefinition(
				"com.vitor.minispring.beans.factory.annotation.internalAutowiredAnnotationProcessor",
				new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
	}

	private String determineBeanName(BeanDefinition beanDefinition) {
		Class<?> beanClass = beanDefinition.getBeanClass();
		Component component = beanClass.getAnnotation(Component.class);
		String value = component.value();
		if (StrUtil.isEmpty(value)) {
			value = StrUtil.lowerFirst(beanClass.getSimpleName());
		}
		return value;
	}

	private String resolveBeanScope(BeanDefinition beanDefinition) {
		Class<?> beanClass = beanDefinition.getBeanClass();
		Scope scope = beanClass.getAnnotation(Scope.class);
		if (null != scope) {
			return scope.value();
		}
		return StrUtil.EMPTY;
	}
}
