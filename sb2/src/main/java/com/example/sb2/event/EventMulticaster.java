package com.example.sb2.event;

import org.springframework.stereotype.Component;

@Component
public interface EventMulticaster {
    // 广播事件
    void multicastEvent(WeatherEvent event);

    // 添加监听器
    void addListener(WeatherListener weatherListener);

    // 删除监听器
    void removeListener(WeatherListener event);

}
