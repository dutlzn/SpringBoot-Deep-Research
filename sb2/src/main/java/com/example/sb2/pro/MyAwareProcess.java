package com.example.sb2.pro;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component

public class MyAwareProcess implements BeanPostProcessor{
    private final ConfigurableApplicationContext configurableApplicationContext;
    public MyAwareProcess( ConfigurableApplicationContext configurableApplicationContext){
        this.configurableApplicationContext = configurableApplicationContext;
    }
    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof Aware){
            if(bean instanceof  MyAware){
                ((MyAware)bean).setFlag((Flag) configurableApplicationContext.getBean("flag"));
            }
        }
        return bean;

    }

}