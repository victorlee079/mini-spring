package com.vitor.minispring.beans.factory;

public interface BeanClassLoaderAware extends Aware {
	void setBeanClassLoader(ClassLoader classLoader);
}
