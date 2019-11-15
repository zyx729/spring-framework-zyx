package com.zyx.spring.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 模拟自定义的后置处理器
 * 与BeanPostProcessor 不同，这里是beanFactory，也就是bean 工厂，我们可以通过获取beanFactory 来对获取指定name 的bean
 * 从而进行干预
 * 比如下面代码中将指定bean 的类型改为原型
 *
 * 同时也可以定义多个后置处理器，最终形成一个类似过滤器的状态来对bean 的初始化过程进行干预
 * 可以通过实现PriorityOrdered 接口及实现它的getOrder() 方法来指定当前后置处理器的权重，值越小越先执行，来控制后置处理器的处理先后顺序。
 */
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) beanFactory.getBeanDefinition("entityBean");
		beanDefinition.setScope("prototype");
	}
}
