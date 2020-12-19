package com.example.sb2;

import com.example.sb2.initializer.SecondInitializer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.sb2.mapper")
public class Sb2Application {

	public static void main(String[] args) {
//		SpringApplication.run(Sb2Application.class, args);
		SpringApplication springApplication = new SpringApplication(Sb2Application.class);
		springApplication.addInitializers(new SecondInitializer());
		springApplication.run(args);
	}

}
