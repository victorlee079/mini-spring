package com.vitor.minispring.core.convert.converter;

public interface Converter<S, T> {
	T convert(S source);
}
