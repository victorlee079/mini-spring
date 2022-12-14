package com.vitor.minispring.beans.factory.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.PropertyValue;
import com.vitor.minispring.beans.factory.config.BeanDefinition;
import com.vitor.minispring.beans.factory.config.BeanReference;
import com.vitor.minispring.context.annotation.ClassPathBeanDefinitionScanner;
import com.vitor.minispring.core.io.Resource;
import com.vitor.minispring.core.io.ResourceLoader;

import cn.hutool.core.util.StrUtil;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		super(registry);
	}

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
		super(registry, resourceLoader);
	}

	@Override
	public void loadBeanDefinitions(Resource resource) throws BeansException {
		try {
			try (InputStream inputStream = resource.getInputStream()) {
				doLoadBeanDefinitions(inputStream);
			}
		} catch (IOException | ClassNotFoundException | DocumentException e) {
			throw new BeansException("IOException parsing XML document from " + resource, e);
		}
	}

	@Override
	public void loadBeanDefinitions(Resource... resources) throws BeansException {
		for (Resource resource : resources) {
			loadBeanDefinitions(resource);
		}
	}

	@Override
	public void loadBeanDefinitions(String location) throws BeansException {
		loadBeanDefinitions(getResourceLoader().getResource(location));
	}

	@Override
	public void loadBeanDefinitions(String... locations) throws BeansException {
		for (String location : locations) {
			loadBeanDefinitions(location);
		}
	}

	private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(inputStream);
		Element root = doc.getRootElement();

		Element componentScan = root.element("component-scan");
		if (null != componentScan) {
			String scanPath = componentScan.attributeValue("base-package");
			if (StrUtil.isEmpty(scanPath)) {
				throw new BeansException("The value of base-package attribute cannot be empty or null");
			}
			scanPackage(scanPath);
		}
		List<Element> beanList = root.elements("bean");

		for (Element bean : beanList) {
			String id = bean.attributeValue("id");
			String name = bean.attributeValue("name");
			String className = bean.attributeValue("class");
			String initMethod = bean.attributeValue("init-method");
			String destroyMethodName = bean.attributeValue("destroy-method");
			String beanScope = bean.attributeValue("scope");

			Class<?> clazz = Class.forName(className);

			String beanName = StrUtil.isNotEmpty(id) ? id : name;

			if (StrUtil.isEmpty(beanName)) {
				beanName = StrUtil.lowerFirst(clazz.getSimpleName());
			}

			BeanDefinition beanDefinition = new BeanDefinition(clazz);
			beanDefinition.setInitMethodName(initMethod);
			beanDefinition.setDestroyMethodName(destroyMethodName);

			if (StrUtil.isNotEmpty(beanScope)) {
				beanDefinition.setScope(beanScope);
			}

			List<Element> propertyList = bean.elements("property");
			for (Element property : propertyList) {
				String attrName = property.attributeValue("name");
				String attrValue = property.attributeValue("value");
				String attrRef = property.attributeValue("ref");
				Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
				PropertyValue propertyValue = new PropertyValue(attrName, value);
				beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
			}
			if (getRegistry().containsBeanDefinition(beanName)) {
				throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
			}

			getRegistry().registerBeanDefinition(beanName, beanDefinition);
		}
	}

	private void scanPackage(String scanPath) {
		String[] basePackages = StrUtil.splitToArray(scanPath, ',');
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
		scanner.doScan(basePackages);
	}

}
