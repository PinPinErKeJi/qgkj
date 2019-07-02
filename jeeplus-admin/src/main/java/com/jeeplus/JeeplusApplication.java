package com.jeeplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableCaching
@EnableWebMvc
@SpringBootApplication
@ServletComponentScan("com.jeeplus")
@ComponentScan(value = "com.jeeplus",lazyInit = true)
@ComponentScan(value = "org.activiti.rest", lazyInit = true)
@ComponentScan({"org.activiti.rest.editor", "org.activiti.rest.diagram"})
public class JeeplusApplication extends SpringBootServletInitializer {

//
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(JeeplusApplication.class);
	}
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(JeeplusApplication.class, args);
	}


}
