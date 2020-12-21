package com.example.sb2.event;


import org.springframework.stereotype.Component;

/**
 * 下雨事件
 */
@Component
public class RainEvent extends WeatherEvent{
    @Override
    public String getWeather() {
        return "rain";
    }
}
