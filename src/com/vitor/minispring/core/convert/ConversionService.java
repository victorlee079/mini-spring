package com.vitor.minispring.core.convert;

public interface ConversionService {

	boolean canConvert(Class<?> sourceType, Class<?> targetType);

	<T> T convert(Object source, Class<T> targetType);

}
