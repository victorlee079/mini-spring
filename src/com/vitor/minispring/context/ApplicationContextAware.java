package com.vitor.minispring.context;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
