package com.vitor.minispring.context.event;

@SuppressWarnings("serial")
public class ContextClosedEvent extends ApplicationContextEvent {

	public ContextClosedEvent(Object source) {
		super(source);
	}

}
