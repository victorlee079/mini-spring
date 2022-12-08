package com.vitor.minispring.context.event;

import com.vitor.minispring.context.ApplicationContext;
import com.vitor.minispring.context.ApplicationEvent;

@SuppressWarnings("serial")
public class ApplicationContextEvent extends ApplicationEvent {

	public ApplicationContextEvent(Object source) {
		super(source);
	}

	public final ApplicationContext getApplicationContext() {
		return (ApplicationContext) getSource();
	}

}
