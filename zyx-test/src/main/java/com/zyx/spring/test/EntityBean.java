package com.zyx.spring.test;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EntityBean {

	@PostConstruct
	public void test(){
		System.out.println("EntityBean");
	}
}
