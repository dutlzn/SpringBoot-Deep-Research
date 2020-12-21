package com.example.sb2.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractEventMulticaster implements EventMulticaster{

//    private List<WeatherListener> listeners = new ArrayList<>();

    @Autowired
    private List<WeatherListener> listeners;

    @Override
    public void multicastEvent(WeatherEvent event) {
        // 开始广播
        doStart();
        // 遍历监听器，播放监听事件
        listeners.forEach(i -> i.onWeatherEvent(event));
        // 广播结束
        doEnd();
    }

    abstract void doStart();
    abstract void doEnd();

    @Override
    public void addListener(WeatherListener weatherListener) {
        listeners.add(weatherListener);
    }

    @Override
    public void removeListener(WeatherListener weatherListener) {
        listeners.remove(weatherListener);
    }
}
