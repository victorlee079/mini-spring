package com.vitor.minispring.context;

import com.vitor.minispring.beans.factory.HierarchicalBeanFactory;
import com.vitor.minispring.beans.factory.ListableBeanFactory;
import com.vitor.minispring.core.io.ResourceLoader;

public interface ApplicationContext
		extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
