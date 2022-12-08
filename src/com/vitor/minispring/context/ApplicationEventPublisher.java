package com.vitor.minispring.context;

public interface ApplicationEventPublisher {
	void publishEvent(ApplicationEvent event);
}
