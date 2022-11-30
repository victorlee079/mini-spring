package com.vitor.minispring.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
	private final List<PropertyValue> propertyValueList = new ArrayList<>();

	public void addPropertyValue(PropertyValue pv) {
		this.propertyValueList.add(pv);
	}

	public PropertyValue[] getPropertyValues() {
		return this.propertyValueList.toArray(new PropertyValue[0]);
	}

	public PropertyValue getPropertyValue(String name) {
		return propertyValueList.stream().filter(pv -> pv.getName().equals(name)).findFirst().orElse(null);
	}
}
