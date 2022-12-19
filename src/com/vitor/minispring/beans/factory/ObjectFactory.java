package com.vitor.minispring.beans.factory;

import com.vitor.minispring.beans.BeansException;

public interface ObjectFactory<T> {
	T getObject() throws BeansException;
}
