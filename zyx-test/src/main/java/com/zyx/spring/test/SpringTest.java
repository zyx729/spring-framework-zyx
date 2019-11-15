package com.zyx.spring.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		annotationConfigApplicationContext.register(SpringConfig.class);
		// ... 将Spring 自动IOC 的过程拆分出来 在中间干预代理过程
		annotationConfigApplicationContext.addBeanFactoryPostProcessor(new TestBeanFactoryPostProcessor());
		annotationConfigApplicationContext.refresh();

		EntityBean entityBean = (EntityBean) annotationConfigApplicationContext.getBean("entityBean");
		EntityBean entityBean1 = (EntityBean) annotationConfigApplicationContext.getBean("entityBean");
		System.out.println(entityBean.hashCode() + " ========= " + entityBean1.hashCode());
	}
}
