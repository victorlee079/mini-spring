package com.vitor.minispring.context.annotation;

import java.util.LinkedHashSet;
import java.util.Set;

import com.vitor.minispring.beans.factory.config.BeanDefinition;

import cn.hutool.core.util.ClassUtil;

public class ClassPathScanningCandidateComponentProvider {
	public Set<BeanDefinition> findCandidateComponents(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<>();
		Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
		for (Class<?> clazz : classes) {
			candidates.add(new BeanDefinition(clazz));
		}
		return candidates;
	}
}
