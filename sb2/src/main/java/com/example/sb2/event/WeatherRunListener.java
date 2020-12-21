package com.example.sb2.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherRunListener {
    @Autowired
    private WeatherEventMulticaster weatherEventMulticaster;


    public void snow() {
        weatherEventMulticaster.multicastEvent(new SnowEvent());
    }

    public void rain(){
        weatherEventMulticaster.multicastEvent(new RainEvent());
    }
}
