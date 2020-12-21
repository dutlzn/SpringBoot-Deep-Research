package com.example.sb2.event;
import org.springframework.stereotype.Component;

@Component
public interface WeatherListener {
    void onWeatherEvent(WeatherEvent event);
    
}
