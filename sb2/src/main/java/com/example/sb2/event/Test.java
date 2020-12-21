package com.example.sb2.event;

public class Test {
    public static void main(String[] args){
        // 首先构造广播器
        WeatherEventMulticaster eventMulticaster = new WeatherEventMulticaster();
        // 构造监听器
        RainListener rainListener = new RainListener();
        SnowListener snowListener = new SnowListener();
        // 添加监听器到广播器中
        eventMulticaster.addListener(rainListener);
        eventMulticaster.addListener(snowListener);
        // 通过广播器发送下雪事件
        eventMulticaster.multicastEvent(new SnowEvent());
        eventMulticaster.multicastEvent(new RainEvent());
        // 移除监听器
        eventMulticaster.removeListener(snowListener);
        // 继续发送事件
        eventMulticaster.multicastEvent(new SnowEvent());
        eventMulticaster.multicastEvent(new RainEvent());
    }
}
