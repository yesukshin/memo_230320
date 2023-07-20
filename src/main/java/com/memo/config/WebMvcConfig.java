package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;

@Configuration//설정을 위한 spring bean
public class WebMvcConfig implements WebMvcConfigurer{
	
	// http://localhost:88/images/shing27_1689841052620/capybara-1732020_960_720.jpg
	// FILE_UPLOAD_PATH = "D:\\sys\\6_spring_project\\memo\\workspace\\images/";	
	
	// 웹 이미지 path와 서버에 업로드된 이미지와 매핑 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // 웹 이미지 패스 http://localhost/images/aaaa_123141212/sun.jpg
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH) ; // 실제파일 위치
	}

}
