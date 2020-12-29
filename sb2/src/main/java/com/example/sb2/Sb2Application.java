package com.example.sb2;

import com.example.sb2.except.AException;
import com.example.sb2.except.BException;
import com.example.sb2.except.CException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StopWatch;

import java.util.Properties;

@SpringBootApplication
@MapperScan("com.example.sb2.mapper")
@PropertySource({"demo.properties"})
public class Sb2Application {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Sb2Application.class, args);

//		异常报告处理类
//		try {
//			throw new CException(new BException(new AException(new Exception("test"))));
//		} catch (Throwable t) {
//			while ( t != null ){
//				System.out.println(t.getClass());
//				t = t.getCause();
//			}
//		}


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


//		计时器测试
//		StopWatch myWatch = new StopWatch("myWatch");
//		myWatch.start("task1");
//		Thread.sleep(2000L);
//		myWatch.stop();
//
//		myWatch.start("task2");
//		Thread.sleep(1000L);
//		myWatch.stop();
//
//		myWatch.start("task3");
//		Thread.sleep(500L);
//		myWatch.stop();
//
//
//		System.out.println(myWatch.prettyPrint());


//		属性配置解析
//   		SpringApplication springApplication = new SpringApplication(Sb2Application.class);
//		Properties properties = new Properties();
//		properties.setProperty("sb2.website.url", "test1");
//		springApplication.setDefaultProperties(properties);
//		springApplication.run(args);

	}

}
