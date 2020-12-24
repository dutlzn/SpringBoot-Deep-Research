package com.example.sb2.ioc.ann;

import com.example.sb2.ioc.xml.Animal;
import com.example.sb2.ioc.xml.Cat;
import com.example.sb2.ioc.xml.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean("dog")
    Animal getDog() {
        return new Dog();
    }

//    @Bean("cat")
//    Animal getCat() {
//        return new Cat();
//    }
}
