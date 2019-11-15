/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
 * Factory hook that allows for custom modification of new bean instances,
 * e.g. checking for marker interfaces or wrapping them with proxies.
 *
 * <p>ApplicationContexts can autodetect BeanPostProcessor beans in their
 * bean definitions and apply them to any beans subsequently created.
 * Plain bean factories allow for programmatic registration of post-processors,
 * applying to all beans created through this factory.
 *
 * <p>Typically, post-processors that populate beans via marker interfaces
 * or the like will implement {@link #postProcessBeforeInitialization},
 * while post-processors that wrap beans with proxies will normally
 * implement {@link #postProcessAfterInitialization}.
 *
 * @author Juergen Hoeller
 * @since 10.10.2003
 * @see InstantiationAwareBeanPostProcessor
 * @see DestructionAwareBeanPostProcessor
 * @see ConfigurableBeanFactory#addBeanPostProcessor
 * @see BeanFactoryPostProcessor
 */

/**
 * BeanPostProcessor 是Spring 框架中提供的一个扩展类点（Spring 中一共五个扩展点）
 * 通过实现BeanPostProcessor 接口，程序员就可以插手bean 实例化的过程，从而减轻了beanFactory 的负担
 *
 * 值得说明的是，这个接口可以设置多个，会形成一个列表，然后依次执行
 * （那么Spring 自己的后置处理器怎么添加进来？我们自己的类上有@Component 注解让Spring 知道要去扫描
 * 而Spring 自己的类却是没有的。通过AbstractApplicationContext类中prepareBeanFactory() 方法中
 * beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
 * 这一行我们可以知道，Spring 自己的后置处理器都是这样手动add 进去的）
 *
 * 比如AOP 就是在bean 实例化期间将切面逻辑织入bean 实例中的
 * AOP 也正是通过beanPostProcessor 和IOC 容器建立起了联系
 * （由Spring 提供的默认的PostProcessor，Spring 提供了很多默认的PostProcessor，下面会一一介绍）
 * 查看类的关系图可以知道Spring 提供了以下的默认实现，因为太多，故而只解释几个常见的
 * 1. ApplicationContextAwareProcessor(acap)
 * 	acap 后置处理器的作用是，当应用程序定义的bean 实现ApplicationContextAware 接口时注入ApplicationContext 对象
 * 	当然这是他的第一个作业，还有其他作用，这就不一一列举，可以参考源码
 * 2. InitDestroyAnnotationBeanPostProcessor
 *  用来处理自定义的初始化方法和销毁方法
 *  前面说过Spring 中提供了3种自定义初始化和销毁方法分别是
 *  一、通过@Bean 指定init - method和destroy - method 属性
 *  二、Bean 实现InitializingBean 接口和实现DisposableBean
 *  三、@PostConstruct 和@PreDestroy
 *  为什么Spring 通过这三种方法都能完成对Bean 生命周期的回调呢？
 *  其实就与InitDestroyAnnotationBeanPostProcessor 有关
 * 3. InstantiationAwareBeanPostProcessor
 * 4. CommonAnnotationBeanPostProcessor
 * 5. AutowiredAnnotationBeanPostProcessor
 * 6. RequiredAnnotationBeanPostProcessor
 * 7. BeanValidationPostProcessor
 * 8. AbstractAutoProxyCreator
 * .....
 */
public interface BeanPostProcessor {

	/**
	 * 在bean 初始化之前执行
	 * Apply this BeanPostProcessor to the given new bean instance <i>before</i> any bean
	 * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
	 * or a custom init-method). The bean will already be populated with property values.
	 * The returned bean instance may be a wrapper around the original.
	 * <p>The default implementation returns the given {@code bean} as-is.
	 * @param bean the new bean instance
	 * @param beanName the name of the bean
	 * @return the bean instance to use, either the original or a wrapped one;
	 * if {@code null}, no subsequent BeanPostProcessors will be invoked
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
	 */
	@Nullable
	default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * 在bean 初始化之后执行
	 * Apply this BeanPostProcessor to the given new bean instance <i>after</i> any bean
	 * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
	 * or a custom init-method). The bean will already be populated with property values.
	 * The returned bean instance may be a wrapper around the original.
	 * <p>In case of a FactoryBean, this callback will be invoked for both the FactoryBean
	 * instance and the objects created by the FactoryBean (as of Spring 2.0). The
	 * post-processor can decide whether to apply to either the FactoryBean or created
	 * objects or both through corresponding {@code bean instanceof FactoryBean} checks.
	 * <p>This callback will also be invoked after a short-circuiting triggered by a
	 * {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation} method,
	 * in contrast to all other BeanPostProcessor callbacks.
	 * <p>The default implementation returns the given {@code bean} as-is.
	 * @param bean the new bean instance
	 * @param beanName the name of the bean
	 * @return the bean instance to use, either the original or a wrapped one;
	 * if {@code null}, no subsequent BeanPostProcessors will be invoked
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
	 * @see org.springframework.beans.factory.FactoryBean
	 */
	@Nullable
	default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
