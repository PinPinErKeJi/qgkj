package com.jeeplus.modules.api.configure;

import com.jeeplus.modules.api.intercept.ApiSbkqIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jeeplus.modules.api.intercept.ApiIntercept;

@Configuration
public class ApiConfigure implements WebMvcConfigurer {

	@Autowired
	private ApiIntercept apiIntercept;
	@Autowired
	private ApiSbkqIntercept apiSbkqIntercept;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiIntercept).addPathPatterns("/api/**");

		registry.addInterceptor(apiSbkqIntercept).addPathPatterns("/api/CloudDate/AppSbkqData");

		registry.addInterceptor(apiSbkqIntercept).addPathPatterns("/api/Ryxx/ryxxAdd");

		registry.addInterceptor(apiSbkqIntercept).addPathPatterns("/api/employee/find");

		registry.addInterceptor(apiSbkqIntercept).addPathPatterns("/api/equipment/binding");
	}
}