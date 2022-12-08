package com.vitor.minispring.context.event;

import com.vitor.minispring.beans.factory.BeanFactory;
import com.vitor.minispring.context.ApplicationEvent;
import com.vitor.minispring.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

	public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
		setBeanFactory(beanFactory);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void multicastEvent(ApplicationEvent event) {
		for (final ApplicationListener listener : getApplicationListeners(event)) {
			listener.onApplicationEvent(event);
		}
	}

}
