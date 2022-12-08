package com.vitor.minispring.context;

import java.util.EventObject;

@SuppressWarnings("serial")
public abstract class ApplicationEvent extends EventObject {

	public ApplicationEvent(Object source) {
		super(source);
	}

}
