package com.example.sb2;

import com.example.sb2.event.WeatherRunListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class Sb2ApplicationTests {

    //  注入
    @Autowired
    private WeatherRunListener weatherRunListener;

    @Test
    void contextLoads() {

    }


    @Test
	public void testEvent() {
    	weatherRunListener.rain();
    	weatherRunListener.snow();
	}

}
