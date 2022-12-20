package com.vitor.minispring.core.convert.support;

import java.util.Set;

import com.vitor.minispring.beans.factory.FactoryBean;
import com.vitor.minispring.beans.factory.InitializingBean;
import com.vitor.minispring.core.convert.ConversionService;
import com.vitor.minispring.core.convert.converter.Converter;
import com.vitor.minispring.core.convert.converter.ConverterFactory;
import com.vitor.minispring.core.convert.converter.ConverterRegistry;
import com.vitor.minispring.core.convert.converter.GenericConverter;

public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

	private Set<?> converters;

	private GenericConversionService conversionService;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.conversionService = new DefaultConversionService();
		registerConverters(converters, conversionService);
	}

	private void registerConverters(Set<?> converters, ConverterRegistry registry) {
		if (converters != null) {
			for (Object converter : converters) {
				if (converter instanceof GenericConverter) {
					registry.addConverter((GenericConverter) converter);
				} else if (converter instanceof Converter<?, ?>) {
					registry.addConverter((Converter<?, ?>) converter);
				} else if (converter instanceof ConverterFactory<?, ?>) {
					registry.addConverterFactory((ConverterFactory<?, ?>) converter);
				} else {
					throw new IllegalArgumentException(
							"Each converter object must implement one of the Converter, ConverterFactory, or GenericConverter interfaces");
				}
			}
		}
	}

	public void setConverters(Set<?> converters) {
		this.converters = converters;
	}

	@Override
	public ConversionService getObject() throws Exception {
		return conversionService;
	}

	@Override
	public Class<?> getObjectType() {
		return conversionService.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
