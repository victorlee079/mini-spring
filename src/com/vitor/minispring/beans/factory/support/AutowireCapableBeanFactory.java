package com.vitor.minispring.beans.factory.support;

import com.vitor.minispring.beans.BeansException;

public interface AutowireCapableBeanFactory {

	Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

	Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
