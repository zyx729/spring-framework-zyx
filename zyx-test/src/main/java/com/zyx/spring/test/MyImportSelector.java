package com.zyx.spring.test;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		importingClassMetadata.getAnnotationTypes().forEach(System.out::println);
		return new String[0];
	}
}
