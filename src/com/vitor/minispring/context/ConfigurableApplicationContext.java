package com.vitor.minispring.context;

import com.vitor.minispring.beans.BeansException;

/**
 * 
 * Provide a method to refresh the configuration
 *
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
	void refresh() throws BeansException;
}
