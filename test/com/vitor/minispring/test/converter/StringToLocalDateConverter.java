package com.vitor.minispring.test.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.vitor.minispring.core.convert.converter.Converter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

	private final DateTimeFormatter DATE_TIME_FORMATTER;

	public StringToLocalDateConverter(String pattern) {
		DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(pattern);
	}

	@Override
	public LocalDate convert(String source) {
		return LocalDate.parse(source, DATE_TIME_FORMATTER);
	}

}
