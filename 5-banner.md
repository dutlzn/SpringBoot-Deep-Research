---
typora-root-url: /images
---

# Banner解析

https://www.cnblogs.com/vicF/archive/2004/01/13/12666675.html



* banner演示
* banner获取原理
* banner输出原理
* 总结



resources banner.txt

```

//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//             佛祖保佑          永无BUG         永不修改                	  //
```



启动 启动类，就可以加载自定义banner



如果不是banner.txt就识别不出来

favorite.txt



属性配置文件



```
server.port=8081
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.url=jdbc:mysql://192.168.56.101:3306/test
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.example.sb2.bean
mybatis.configuration.map-underscore-to-camel-case=true

# 初始化器配置
context.initializer.classes=com.example.sb2.initializer.ThirdInitializer
# 自定义监听器
context.listener.classes=com.example.sb2.listener.ThirdListener

WUDI=test


spring.banner.location=favorite.txt
```



## 图案打印

准备google图片， resources banner.jpg

banner.txt  -> banner_bak.txt 防止干扰

同样的图片位置自定义

spring.banner.image.location=..



## 自定义读取一个banner

![](/50.png)



## 不想打印banner

```
server.port=8081
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.url=jdbc:mysql://192.168.56.101:3306/test
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.example.sb2.bean
mybatis.configuration.map-underscore-to-camel-case=true

# 初始化器配置
context.initializer.classes=com.example.sb2.initializer.ThirdInitializer
# 自定义监听器
context.listener.classes=com.example.sb2.listener.ThirdListener

WUDI=test


#spring.banner.location=favorite.txt

#关闭banner打印
spring.main.banner-mode=off
```



## 总结

* 默认banner spring 什么都不用设置
* 文字banner 设置banner.txt 或者设置spring.banner.location
* 设置banner.(gif.|png|jpg) 或者设置spring.banner.image.location
* 设置兜底banner springApplication.setBanner()
* 关闭banner  设置spring.main.banner-mode=off

