package com.vitor.minispring.beans.factory.support;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.vitor.minispring.beans.BeansException;
import com.vitor.minispring.beans.PropertyValue;
import com.vitor.minispring.beans.factory.config.BeanDefinition;
import com.vitor.minispring.beans.factory.config.BeanReference;
import com.vitor.minispring.core.io.Resource;
import com.vitor.minispring.core.io.ResourceLoader;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;

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
		} catch (IOException | ClassNotFoundException e) {
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

	private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
		Document doc = XmlUtil.readXML(inputStream);
		Element root = doc.getDocumentElement();
		NodeList childNodes = root.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			if (!(childNodes.item(i) instanceof Element)) {
				continue;
			}

			if (!"bean".equals(childNodes.item(i).getNodeName())) {
				continue;
			}

			Element bean = (Element) childNodes.item(i);
			String id = bean.getAttribute("id");
			String name = bean.getAttribute("name");
			String className = bean.getAttribute("class");
			String initMethod = bean.getAttribute("init-method");
			String destroyMethodName = bean.getAttribute("destroy-method");

			Class<?> clazz = Class.forName(className);

			String beanName = StrUtil.isNotEmpty(id) ? id : name;

			if (StrUtil.isEmpty(beanName)) {
				beanName = StrUtil.lowerFirst(clazz.getSimpleName());
			}

			BeanDefinition beanDefinition = new BeanDefinition(clazz);
			beanDefinition.setInitMethodName(initMethod);
			beanDefinition.setDestroyMethodName(destroyMethodName);

			for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
				if (!(bean.getChildNodes().item(j) instanceof Element)) {
					continue;
				}
				if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) {
					continue;
				}

				Element property = (Element) bean.getChildNodes().item(j);
				String attrName = property.getAttribute("name");
				String attrValue = property.getAttribute("value");
				String attrRef = property.getAttribute("ref");
				Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
				PropertyValue propertyValue = new PropertyValue(attrName, value);
				beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
			}

			getRegistry().registerBeanDefinition(beanName, beanDefinition);
		}
	}

}
