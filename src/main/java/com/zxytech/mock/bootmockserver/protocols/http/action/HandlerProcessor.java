package com.zxytech.mock.bootmockserver.protocols.http.action;

import com.google.common.collect.Maps;
import com.zxytech.mock.bootmockserver.protocols.http.action.domain.HttpMockActionType;
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
      Map<String, HttpMockActionHandler> handlerMap =
          Maps.newHashMapWithExpectedSize(beanDefinitionSet.size());

    for (BeanDefinition beanDefinition : beanDefinitionSet) {
      try {
          String className = beanDefinition.getBeanClassName();
          logger.info(className);
          if (className == null) {
              continue;
          }
          Class<?> cls = Class.forName(className);
          String actionType = cls.getAnnotation(HttpMockActionType.class).value();
          logger.info(actionType);
          HttpMockActionHandler actionHandler = (HttpMockActionHandler) cls.newInstance();
          // 注册bean单例，减少python解释器之类的重复创建
          beanFactory.registerSingleton(className, actionHandler);
          handlerMap.put(actionType, actionHandler);
      } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
        logger.error("HandlerProcessor BeanDefinition", e);
      }
    }
      logger.info(handlerMap.keySet().toString());
    HandlerContext handlerContext = new HandlerContext(handlerMap);
      // 注册bean以备autowire引用
    beanFactory.registerSingleton(HandlerContext.class.getName(), handlerContext);
  }
}
