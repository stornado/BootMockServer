package com.zxytech.mock.bootmockserver.protocols.http.action;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandlerProcessor implements BeanFactoryPostProcessor {

  private static final String HANDLER_PACKAGE = HandlerProcessor.class.getPackage().getName();

  @Override
  public void postProcessBeanFactory(
      ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    ClassPathScanningCandidateComponentProvider provider =
        new ClassPathScanningCandidateComponentProvider(false);

    provider.addIncludeFilter(new AnnotationTypeFilter(HttpMockActionType.class));
    provider.addIncludeFilter(new AssignableTypeFilter(HttpMockActionHandler.class));
    Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(HANDLER_PACKAGE);
      Map<String, HttpMockActionHandler> handlerMap = new HashMap<>();
      for(BeanDefinition beanDefinition: beanDefinitionSet) {
//          handlerMap.put(beanDefinition.getBeanClassName(), beanDefinition.)
//          beanDefinition.getBeanClassName().
      }
  }
}
