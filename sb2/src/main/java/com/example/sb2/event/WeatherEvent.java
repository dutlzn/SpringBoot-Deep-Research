package com.example.sb2.event;
import org.springframework.stereotype.Component;

@Component
public abstract class WeatherEvent {
    public abstract String getWeather();
}
