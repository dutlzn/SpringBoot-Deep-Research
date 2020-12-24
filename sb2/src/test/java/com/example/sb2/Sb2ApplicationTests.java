package com.example.sb2;

import com.example.sb2.ioc.ann.MyBeanImport;
import com.example.sb2.ioc.xml.HelloService;
import com.example.sb2.event.WeatherRunListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Import(MyBeanImport.class)
//@ContextConfiguration(locations = "classpath:ioc/demo.xml")
public class Sb2ApplicationTests {

    @Autowired
    private HelloService helloService;


    @Test
    public void testHello() {
        System.out.println(helloService.hello());
        System.out.println(helloService.hello2());
    }

//	@Autowired
//	private WeatherRunListener weatherRunListener;

//
//	@Before
//	public void init() {
//		System.out.println("开始测试-----------------");
//	}
//
//	@After
//	public void after() {
//		System.out.println("测试结束-----------------");
//	}
//
//
//	@Test
//	public void testEvent() {
//		weatherRunListener.rain();
//		weatherRunListener.snow();
//	}


}
