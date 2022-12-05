package com.vitor.minispring.beans.factory;

public interface InitializingBean {
	void afterPropertiesSet() throws Exception;
}
