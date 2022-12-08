package com.vitor.minispring.context.event;

@SuppressWarnings("serial")
public class ContextRefreshedEvent extends ApplicationContextEvent {

	public ContextRefreshedEvent(Object source) {
		super(source);
	}

}
