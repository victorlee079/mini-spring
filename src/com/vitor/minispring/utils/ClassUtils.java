package com.vitor.minispring.utils;

public class ClassUtils {

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader
			// Falling back to system class loader
		}

		if (cl == null) {
			cl = ClassUtils.getDefaultClassLoader();
		}
		return cl;
	}

	public static boolean isCglibProxyClass(Class<?> clazz) {
		return (clazz != null && isCglibProxyClassName(clazz.getName()));
	}

	public static boolean isCglibProxyClassName(String className) {
		return (className != null && className.contains("$$"));
	}
}
