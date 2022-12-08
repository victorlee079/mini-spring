package com.vitor.minispring.context.event;

import com.vitor.minispring.context.ApplicationEvent;
import com.vitor.minispring.context.ApplicationListener;

public interface ApplicationEventMulticaster {
	void addApplicationListener(ApplicationListener<?> listener);

	void removeApplicationListener(ApplicationListener<?> listener);

	void multicastEvent(ApplicationEvent event);
}
