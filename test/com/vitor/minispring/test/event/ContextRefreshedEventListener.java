package com.vitor.minispring.test.event;

import com.vitor.minispring.context.ApplicationListener;
import com.vitor.minispring.context.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("刷新事件：" + this.getClass().getName());
	}

}