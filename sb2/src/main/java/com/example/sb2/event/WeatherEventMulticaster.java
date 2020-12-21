package com.example.sb2.event;

import org.springframework.stereotype.Component;

@Component
public class WeatherEventMulticaster extends AbstractEventMulticaster{
    @Override
    void doStart() {
        System.out.println("开始广播");
    }

    @Override
    void doEnd() {
        System.out.println("结束广播");
    }
}
