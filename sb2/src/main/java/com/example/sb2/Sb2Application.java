package com.example.sb2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@MapperScan("com.example.sb2.mapper")
public class Sb2Application {

	public static void main(String[] args) {
		SpringApplication.run(Sb2Application.class, args);

//		第二种自定义初始化器
//		SpringApplication springApplication = new SpringApplication(Sb2Application.class);
//		springApplication.addInitializers(new SecondInitializer());
//		springApplication.run(args);

//		// 自定义监听器
//		SpringApplication springApplication = new SpringApplication(Sb2Application.class);
//		springApplication.addListeners(new SecondListener());
//		springApplication.run(args);


//		自定义加载banner
//		SpringApplication springApplication = new SpringApplication(Sb2Application.class);
//		springApplication.setBanner(new ResourceBanner(new ClassPathResource("banner_bak.txt")));
//		springApplication.run(args);
	}

}
