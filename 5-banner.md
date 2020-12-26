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





# 获取Banner

启动类

```java
package com.example.sb2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@MapperScan("com.example.sb2.mapper")
public class Sb2Application {

   public static void main(String[] args) {
      SpringApplication.run(Sb2Application.class, args);

//    第二种自定义初始化器
//    SpringApplication springApplication = new SpringApplication(Sb2Application.class);
//    springApplication.addInitializers(new SecondInitializer());
//    springApplication.run(args);

//    // 自定义监听器
//    SpringApplication springApplication = new SpringApplication(Sb2Application.class);
//    springApplication.addListeners(new SecondListener());
//    springApplication.run(args);


//    自定义加载banner
//    SpringApplication springApplication = new SpringApplication(Sb2Application.class);
//    springApplication.setBanner(new ResourceBanner(new ClassPathResource("banner_bak.txt")));
//    springApplication.run(args);
   }

}
```



```java
/**
 * Run the Spring application, creating and refreshing a new
 * {@link ApplicationContext}.
 * @param args the application arguments (usually passed from a Java main method)
 * @return a running {@link ApplicationContext}
 */
public ConfigurableApplicationContext run(String... args) {
   StopWatch stopWatch = new StopWatch();
   stopWatch.start();
   ConfigurableApplicationContext context = null;
   Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
   configureHeadlessProperty();
   SpringApplicationRunListeners listeners = getRunListeners(args);
   listeners.starting();
   try {
      ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
      ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
      configureIgnoreBeanInfo(environment);
      // 关注这里
      Banner printedBanner = printBanner(environment);
      context = createApplicationContext();
      exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
            new Class[] { ConfigurableApplicationContext.class }, context);
      prepareContext(context, environment, listeners, applicationArguments, printedBanner);
      refreshContext(context);
      afterRefresh(context, applicationArguments);
      stopWatch.stop();
      if (this.logStartupInfo) {
         new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
      }
      listeners.started(context);
      callRunners(context, applicationArguments);
   }
   catch (Throwable ex) {
      handleRunFailure(context, ex, exceptionReporters, listeners);
      throw new IllegalStateException(ex);
   }

   try {
      listeners.running(context);
   }
   catch (Throwable ex) {
      handleRunFailure(context, ex, exceptionReporters, null);
      throw new IllegalStateException(ex);
   }
   return context;
}
```



printBanner

```java
private Banner printBanner(ConfigurableEnvironment environment) {
   if (this.bannerMode == Banner.Mode.OFF) {
      return null;
   }
   ResourceLoader resourceLoader = (this.resourceLoader != null) ? this.resourceLoader
         : new DefaultResourceLoader(null);
   SpringApplicationBannerPrinter bannerPrinter = new SpringApplicationBannerPrinter(resourceLoader, this.banner);
   if (this.bannerMode == Mode.LOG) {
      return bannerPrinter.print(environment, this.mainApplicationClass, logger);
   }
   return bannerPrinter.print(environment, this.mainApplicationClass, System.out);
}
```



```java
Banner print(Environment environment, Class<?> sourceClass, PrintStream out) {
   Banner banner = getBanner(environment);
   banner.printBanner(environment, sourceClass, out);
   return new PrintedBanner(banner, sourceClass);
}
```





输出banner逻辑: 获取banner  打印banner



![](/51.png)





# Banner内容输出原理解析

看 SpringBootBanner

```java
@Override
public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
   for (String line : BANNER) {
      printStream.println(line);
   }
   String version = SpringBootVersion.getVersion();
   version = (version != null) ? " (v" + version + ")" : "";
   StringBuilder padding = new StringBuilder();
   while (padding.length() < STRAP_LINE_SIZE - (version.length() + SPRING_BOOT.length())) {
      padding.append(" ");
   }

   printStream.println(AnsiOutput.toString(AnsiColor.GREEN, SPRING_BOOT, AnsiColor.DEFAULT, padding.toString(),
         AnsiStyle.FAINT, version));
   printStream.println();
}
```





![](/52.png)







## 默认输出

* 先输出banner指定内容
* 获取version信息
* 文本内容前后对齐
* 文本内容染色
* 输出文本内容





## 文本输出

* 可以通过spring.banner.charset指定字符集
* 获取文本内容
* 替换占位符
* 输出文本内容

## 图片输出

* 可以通过spring.banner.image.*设置图片属性
* 读取图片文件流
* 输出图片内容







# 总结



## 面试题



* 举例banner常见配置方式
* 简述下框架内banner打印流程步骤
* 说明下banner获取原理
* 说明下banner输出原理
* 说出你熟悉的banner属性有哪些



