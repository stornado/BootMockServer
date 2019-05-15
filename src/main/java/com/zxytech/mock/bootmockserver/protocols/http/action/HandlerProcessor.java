package com.zxytech.mock.bootmockserver.protocols.http.action;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@Component
public class HandlerProcessor implements BeanFactoryPostProcessor {
  private static final Logger logger = LoggerFactory.getLogger(HandlerProcessor.class);

  private static final String HANDLER_PACKAGE = HandlerProcessor.class.getPackage().getName();

  @Override
  public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    ClassPathScanningCandidateComponentProvider provider =
        new ClassPathScanningCandidateComponentProvider(false);

    provider.addIncludeFilter(new AnnotationTypeFilter(HttpMockActionType.class));
    provider.addIncludeFilter(new AssignableTypeFilter(HttpMockActionHandler.class));
    Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(HANDLER_PACKAGE);
    Map<String, HttpMockActionHandler> handlerMap = Maps.newHashMapWithExpectedSize(2);

    for (BeanDefinition beanDefinition : beanDefinitionSet) {
      try {
        handlerMap.put(
            Class.forName(beanDefinition.getBeanClassName())
                .getAnnotation(HttpMockActionType.class)
                .value(),
            (HttpMockActionHandler) Class.forName(beanDefinition.getBeanClassName()).newInstance());
      } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
        logger.error("HandlerProcessor BeanDefinition", e);
      }
    }
      logger.info(handlerMap.keySet().toString());
    HandlerContext handlerContext = new HandlerContext(handlerMap);
    beanFactory.registerSingleton(HandlerContext.class.getName(), handlerContext);
  }
}
