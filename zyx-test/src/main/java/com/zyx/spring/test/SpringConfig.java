package com.zyx.spring.test;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan("com.zyx")
@Configuration
public class SpringConfig {


	/**
	 * 因为这里是加了@Bean 的注解，所以Spring 会通过getA() 方法返回一个A 对象
	 * 而Spring 在构建A 对象的时候，会根据该方法的返回也就是new A() 来定位A 的构造方法来判断如何进行对象创建
	 * 因为有可能A 有多个构造方法，如果有带参的构造方法，同时这里也不指定返回的是new A()
	 * 那么Spring 是不知道如何去构造A
	 *
	 * 当这里指定了A 的构造方式是调用无参构造方法构造的时候，A 的无参构造方法就会被Spring 进行调用
	 * 也就是会输出A - init
	 *
	 * 在getB() 方法中，我们调用了getA()，同时因为getB() 上也加了@Bean 注解，所以Spring 在构造B 的过程中
	 * 会去执行getB() 中的代码
	 * 也就是会去调用getA() 方法，将new A() 再执行一次
	 * 所以，当我们SpringConfig 类上没有加@Configuration 注解时A - init 语句会被输出2 次。
	 * 但是当我们SpringConfig 类上加了@Configuration 注解后，只会输出1 次A - init
	 *
	 * 在Spring 中当我们使用javaconfig 的写法进行配置的时候，加了@Configuration 注解的类我们称为full，反之我们称为lite
	 * 当一个类是Full 的时候，为了避免这种一个对象多次调用被多次创建的情况，Spring 利用cglib 代理了配置类config，
	 * 当需要多次调用时，cglib 代理会去判断当前的调用是否是第一次调用，如果是则进入方法去new 对象(当方法有对象返回时，
	 * 方法体内一定有创建对象的过程及最终将对象返回的操作，当判断为第一次创建对象的时候，就直接让cglib 调用父类也就是原始类的创建流程去创建对象)
	 * 如果不是第一次调用，cglib 就会通过实现MethodInterceptor 接口的拦截器去拦截请求，并将对象从beanFactory 中取出重复使用，而不是去进行创建
	 * 当拿出来之后再继续后续的代码流程。
	 *
	 * 这一点还需要强化查询一下，细节部分还不是特别清楚，比如cglib 代理的实现过程是怎么样的
	 * org.springframework.context.annotation.ConfigurationClassEnhancer.BeanMethodInterceptor#isCurrentlyInvokedFactoryMethod(java.lang.reflect.Method)
	 * 到底是不是视频中说的判断是不是第一次创建对象
	 * @return
	 */
	@Bean
	public A getA() {
		System.out.println("aaaaaaaa");
		getB();
		return new A();
	}

	@Bean
	public B getB() {
		System.out.println("bbbbbbb");
		return new B();
	}


	public void getC(){
		System.out.println("cccccc");
	}

}
