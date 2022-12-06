package com.vitor.minispring.beans.factory;

import com.vitor.minispring.beans.BeansException;

public interface BeanFactoryAware extends Aware {
	void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
