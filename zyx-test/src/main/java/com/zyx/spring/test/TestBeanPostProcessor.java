package com.zyx.spring.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * 模拟自定义的后置处理器
 * 在后置处理器中的两个需要我们实现的方法中(BeanPostProcessor 中新定义了这两个方法@Nullable 表示可以不实现)，都提供了
 * bean 和beanName，所以，我们可以在这两个实现方法过程中，对目标bean 进行我们自定义的处理，最终只要将该bean 进行返回即可，
 * 包括对这个bean 进行代理。
 *
 * 同时也可以定义多个后置处理器，最终形成一个类似过滤器的状态来对bean 的初始化过程进行干预
 * 可以通过实现PriorityOrdered 接口及实现它的getOrder() 方法来指定当前后置处理器的权重，值越小越先执行，来控制后置处理器的处理先后顺序。
 */
public class TestBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(beanName.equals("entityBean")){
			System.out.println("postProcessBeforeInitialization");
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(beanName.equals("entityBean")){
			System.out.println("postProcessAfterInitialization");
		}

		return bean;
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
