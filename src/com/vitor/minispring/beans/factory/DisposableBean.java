package com.vitor.minispring.beans.factory;

public interface DisposableBean {
	void destroy() throws Exception;
}
