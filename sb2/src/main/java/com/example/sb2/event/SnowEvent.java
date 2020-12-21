package com.example.sb2.event;

import org.springframework.stereotype.Component;

/**
 * 下雪事件
 */
@Component
public class SnowEvent extends WeatherEvent{
    @Override
    public String getWeather() {
        return "snow";
    }
}
