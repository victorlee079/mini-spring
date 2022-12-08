package com.vitor.minispring.test.event;

import java.util.Date;

import com.vitor.minispring.context.ApplicationListener;

public class CustomEventListener implements ApplicationListener<CustomEvent> {

	@Override
	public void onApplicationEvent(CustomEvent event) {
		System.out.println("Received:" + event.getSource() + "message;time:" + new Date());
		System.out.println("Message:" + event.getId() + ":" + event.getMessage());
	}

}
