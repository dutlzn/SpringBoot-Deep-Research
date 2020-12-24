---
typora-root-url: /images
---

# Bean解析

* IOC思想

* XML方式配置bean

* 注解方式配置bean

* refresh方法（spring里的核心方法）

* bean实例化

  

# IOC思想解析

![](/37.png)

这几个对象相互依赖

在一个类里调用另外的类 早期的java需要手动new 这个对象

举例说明，假设有一个animal类有两个实现Dog cat

如果实现类变了，要去手动修改，

Animal animal = new Dog();

Animal animal = new Cat();

用了spring 

@Autowired

Animal animal; 不用关注它内部的实现



![](/38.png)

使用一个装置，将四个容器隔离开来。

不需要让每个容器知道对方的存在  通过中间的容器带动大家一起运行。

去掉中间的容器，四个容器感知不到其他容器的存在，耦合性降低



参考：https://www.jianshu.com/p/d430c6f07566

IOC（控制反转）：全称为：Inverse of Control。从字面上理解就是控制反转了，将对在自身对象中的一个内置对象的控制反转，反转后不再由自己本身的对象进行控制这个内置对象的创建，而是由第三方系统去控制这个内置对象的创建。

DI（依赖注入）：全称为Dependency Injection，意思自身对象中的内置对象是通过注入的方式进行创建。

那么IOC和DI这两者又是什么关系呢？
 IOC就是一种软件设计思想，DI是这种软件设计思想的一个实现。
 把本来在类内部控制的对象，反转到类外部进行创建后注入，不再由类本身进行控制，这就是IOC的本质。



## IOC优点

* 松耦合 感知不到对方的存在

* 灵活性 不需要在类中去调用使用类的构造函数

* 可维护性  可以知道哪个地方用到了animal类

  

## Bean配置方式

* xml
* 注解

# xml方式配置bean

* 无参构造

* 有参构造

* 静态工厂方法

* 实例工厂方法



定义一个包IOC 下面定义一个包xml

定义student

```java
package com.example.ioc.xml;

import java.util.List;

public class Student {
    private String name;

    private int age;

    private List<String> classList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getClassList() {
        return classList;
    }

    public void setClassList(List<String> classList) {
        this.classList = classList;
    }

    @Override
    public String toString() {
        return "Studeng{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", classList=" + String.join(",", classList) +
                '}';
    }
}

```





在resources 下创建ioc目录 ，创建demo.xml文件

```xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="student" class="com.example.sb2.ioc.xml.Student">
        <property name="name" value="zhangsan"/>
        <property name="age" value="13"/>
        <property name="classList" >
            <list>
                <value>math</value>
                <value>english</value>
            </list>
        </property>
    </bean>
</beans>

```


定义服务类

```java
package com.example.sb2.IOC.xml;

public class HelloService {
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public  String hello() {
        return student.toString();
    }
}

```





xml定义属性

```xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="student" class="com.example.sb2.ioc.xml.Student">
        <property name="name" value="zhangsan"/>
        <property name="age" value="13"/>
        <property name="classList" >
            <list>
                <value>math</value>
                <value>english</value>
            </list>
        </property>
    </bean>

    <bean id="helloService" class="com.example.sb2.ioc.xml.HelloService">
        <property name="student" ref="student" />
    </bean>

</beans>

```





测试类

test包下面

```java
package com.example.sb2;

import com.example.sb2.ioc.xml.HelloService;
import com.example.sb2.event.WeatherRunListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:ioc/demo.xml")
public class Sb2ApplicationTests {

    @Autowired
    private HelloService helloService;


    @Test
    public void testHello() {
        System.out.println(helloService.hello());
    }

//	@Autowired
//	private WeatherRunListener weatherRunListener;

//
//	@Before
//	public void init() {
//		System.out.println("开始测试-----------------");
//	}
//
//	@After
//	public void after() {
//		System.out.println("测试结束-----------------");
//	}
//
//
//	@Test
//	public void testEvent() {
//		weatherRunListener.rain();
//		weatherRunListener.snow();
//	}


}

```



要注意xml 文件最上面不要空行

启动测试类 发现student 属性被注入了



## 有参构造注入

```java
package com.example.sb2.ioc.xml;

import java.util.List;

public class Student {
    private String name;

    private int age;

    private List<String> classList;
    // 有参构造
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getClassList() {
        return classList;
    }

    public void setClassList(List<String> classList) {
        this.classList = classList;
    }

    @Override
    public String toString() {
        return "Studeng{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", classList=" + String.join(",", classList) +
                '}';
    }
}

```



修改xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="student" class="com.example.sb2.ioc.xml.Student">
<!--        有参构造函数-->
        <constructor-arg index="0" value="zhangsan" />
        <constructor-arg index="1" value="123" />
<!--        <property name="name" value="zhangsan"/>-->
<!--        <property name="age" value="13"/>-->
        <property name="classList" >
            <list>
                <value>math</value>
                <value>english</value>
            </list>
        </property>
    </bean>

    <bean id="helloService" class="com.example.sb2.ioc.xml.HelloService">
        <property name="student" ref="student" />
    </bean>

</beans>

```



启动测试类

没问题





## 静态工厂方法

```
package com.example.sb2.ioc.xml;

public abstract  class Animal {
    abstract  String getName();
}
```





```
package com.example.sb2.ioc.xml;

public class Cat extends Animal{
    @Override
    String getName() {
        return "cat";
    }
}
```



```
package com.example.sb2.ioc.xml;

public class Dog extends Animal{
    @Override
    String getName() {
        return "dog";
    }
}
```





```
package com.example.sb2.ioc.xml;

public class AnimalFactory {
    public static Animal getAnimal(String type) {
        if("dog".equals(type)){
            return new Dog();
        } else {
           return new Cat();
        }
    }
}
```



修改helloService.java

```java
package com.example.sb2.ioc.xml;

//public class HelloService {
//    private Student student;
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public  String hello() {
//        return student.toString();
//    }
//}



public class HelloService {
    private Student student;


    private Animal animal;


    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String hello() {
        return student.toString();
    }

    public String hello2() {
        return animal.getName();
    }
}

```





xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="student" class="com.example.sb2.ioc.xml.Student">
<!--        有参构造函数-->
        <constructor-arg index="0" value="zhangsan" />
        <constructor-arg index="1" value="123" />
<!--        <property name="name" value="zhangsan"/>-->
<!--        <property name="age" value="13"/>-->
        <property name="classList" >
            <list>
                <value>math</value>
                <value>english</value>
            </list>
        </property>
    </bean>


    <bean id="helloService" class="com.example.sb2.ioc.xml.HelloService">
        <property name="student" ref="student" />
        <property name="animal" ref="dog" />
    </bean>

    <bean id="dog" class="com.example.sb2.ioc.xml.AnimalFactory" factory-method="getAnimal">
        <constructor-arg value="dog" />
    </bean>

    <bean id="cat" class="com.example.sb2.ioc.xml.AnimalFactory" factory-method="getAnimal">
        <constructor-arg value="cat" />
    </bean>


</beans>

```





测试类

```java
package com.example.sb2;

import com.example.sb2.ioc.xml.HelloService;
import com.example.sb2.event.WeatherRunListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:ioc/demo.xml")
public class Sb2ApplicationTests {

    @Autowired
    private HelloService helloService;


    @Test
    public void testHello() {
        System.out.println(helloService.hello());
        System.out.println(helloService.hello2());
    }

//	@Autowired
//	private WeatherRunListener weatherRunListener;

//
//	@Before
//	public void init() {
//		System.out.println("开始测试-----------------");
//	}
//
//	@After
//	public void after() {
//		System.out.println("测试结束-----------------");
//	}
//
//
//	@Test
//	public void testEvent() {
//		weatherRunListener.rain();
//		weatherRunListener.snow();
//	}


}

```





## 实例工厂方法

改造AnimalFactory

```java
package com.example.sb2.IOC.xml;

public class AnimalFactory {
    public  Animal getAnimal(String type) {
        if("dog".equals(type)){
            return new Dog();
        } else {
            return new Cat();
        }
    }
}

```



修改demo.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="student" class="com.example.sb2.ioc.xml.Student">
<!--        有参构造函数-->
        <constructor-arg index="0" value="zhangsan" />
        <constructor-arg index="1" value="123" />
<!--        <property name="name" value="zhangsan"/>-->
<!--        <property name="age" value="13"/>-->
        <property name="classList" >
            <list>
                <value>math</value>
                <value>english</value>
            </list>
        </property>
    </bean>


    <bean id="helloService" class="com.example.sb2.ioc.xml.HelloService">
        <property name="student" ref="student" />
        <property name="animal" ref="dog" />
    </bean>

    <bean name="animalFactory" class="com.example.sb2.ioc.xml.AnimalFactory"/>


    <bean id="dog" class="com.example.sb2.ioc.xml.AnimalFactory" factory-bean="animalFactory" factory-method="getAnimal">
        <constructor-arg value="dog" />
    </bean>

    <bean id="cat" class="com.example.sb2.ioc.xml.AnimalFactory" factory-bean="animalFactory"  factory-method="getAnimal">
        <constructor-arg value="cat" />
    </bean>


</beans>

```



优点：

* 低耦合
* 对象关系比较清晰 通过xml 知道哪些类有依赖关系
* 集中管理

缺点：

* 配置繁琐
* 开发效率低
* 文件解析耗时



# 注解方式配置Bean

* @Component
* 配置类中使用@Bean
* 实现FactoryBean
* 实现BeanDefinitonRegistryPostProcessor
* 实现ImportBeanDefinitionRegistry



## @Component

xml里注释掉服务

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="student" class="com.example.sb2.ioc.xml.Student">
<!--        有参构造函数-->
        <constructor-arg index="0" value="zhangsan" />
        <constructor-arg index="1" value="123" />
<!--        <property name="name" value="zhangsan"/>-->
<!--        <property name="age" value="13"/>-->
        <property name="classList" >
            <list>
                <value>math</value>
                <value>english</value>
            </list>
        </property>
    </bean>


<!--    <bean id="helloService" class="com.example.sb2.ioc.xml.HelloService">-->
<!--        <property name="student" ref="student" />-->
<!--        <property name="animal" ref="dog" />-->
<!--    </bean>-->

    <bean name="animalFactory" class="com.example.sb2.ioc.xml.AnimalFactory"/>


    <bean id="dog" class="com.example.sb2.ioc.xml.AnimalFactory" factory-bean="animalFactory" factory-method="getAnimal">
        <constructor-arg value="dog" />
    </bean>

    <bean id="cat" class="com.example.sb2.ioc.xml.AnimalFactory" factory-bean="animalFactory"  factory-method="getAnimal">
        <constructor-arg value="cat" />
    </bean>


</beans>
```



helloservice.java

```java
package com.example.sb2.ioc.xml;

//public class HelloService {
//    private Student student;
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public  String hello() {
//        return student.toString();
//    }
//}


import org.springframework.stereotype.Component;

@Component
public class HelloService {
    private Student student;


    private Animal animal;


    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String hello() {
//        return student.toString();
        return "hello";
    }


    public String hello2() {
        return animal.getName();
    }
}

```





测试类

```java
package com.example.sb2;

import com.example.sb2.ioc.xml.HelloService;
import com.example.sb2.event.WeatherRunListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
//@ContextConfiguration(locations = "classpath:ioc/demo.xml")
public class Sb2ApplicationTests {

    @Autowired
    private HelloService helloService;


    @Test
    public void testHello() {
        System.out.println(helloService.hello());
//        System.out.println(helloService.hello2());
    }

//	@Autowired
//	private WeatherRunListener weatherRunListener;

//
//	@Before
//	public void init() {
//		System.out.println("开始测试-----------------");
//	}
//
//	@After
//	public void after() {
//		System.out.println("测试结束-----------------");
//	}
//
//
//	@Test
//	public void testEvent() {
//		weatherRunListener.rain();
//		weatherRunListener.snow();
//	}


}

```



启动测试





## 在配置类中注入bean

配置类

```java

package com.example.sb2.ioc.ann;

import com.example.sb2.ioc.xml.Animal;
import com.example.sb2.ioc.xml.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean("dog")
    Animal getDog() {
        return new Dog();
    }
}
```

如果配置多个bean就会报错



修改helloService.java

```java
package com.example.sb2.ioc.xml;

//public class HelloService {
//    private Student student;
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public  String hello() {
//        return student.toString();
//    }
//}


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloService {
    private Student student;

    @Autowired
    private Animal animal;


    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String hello() {
//        return student.toString();
        return "hello";
    }


    public String hello2() {
        return animal.getName();
    }
}

```





修改测试类

```java
package com.example.sb2;

import com.example.sb2.ioc.xml.HelloService;
import com.example.sb2.event.WeatherRunListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
//@ContextConfiguration(locations = "classpath:ioc/demo.xml")
public class Sb2ApplicationTests {

    @Autowired
    private HelloService helloService;


    @Test
    public void testHello() {
        System.out.println(helloService.hello());
        System.out.println(helloService.hello2());
    }

//	@Autowired
//	private WeatherRunListener weatherRunListener;

//
//	@Before
//	public void init() {
//		System.out.println("开始测试-----------------");
//	}
//
//	@After
//	public void after() {
//		System.out.println("测试结束-----------------");
//	}
//
//
//	@Test
//	public void testEvent() {
//		weatherRunListener.rain();
//		weatherRunListener.snow();
//	}


}

```





## 实现FactoryBean

实现一个FactoryBean

```JAVA
package com.example.sb2.ioc.ann;

import com.example.sb2.ioc.xml.Animal;
import com.example.sb2.ioc.xml.Cat;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component("mycat")
public class MyCat implements FactoryBean<Animal> {
    @Override
    public Animal getObject() throws Exception {
        return new Cat();
    }

    @Override
    public Class<?> getObjectType() {
        return Animal.class;
    }
}

```



helloService.java

```java
package com.example.sb2.ioc.xml;

//public class HelloService {
//    private Student student;
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public  String hello() {
//        return student.toString();
//    }
//}


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HelloService {
    private Student student;

    @Autowired
    @Qualifier("mycat")
    private Animal animal;


    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String hello() {
//        return student.toString();
        return "hello";
    }


    public String hello2() {
        return animal.getName();
    }
}

```





## 实现BeanDefinitonRegistryPostProcessor

定义Monkey

```java
package com.example.sb2.ioc.xml;

import com.example.sb2.ioc.xml.Animal;

public class Monkey extends Animal {
    @Override
    String getName() {
        return "monkey";
    }
}

```





```java
package com.example.sb2.ioc.ann;

import com.example.sb2.ioc.xml.Monkey;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyBeanRegister implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(Monkey.class);
        beanDefinitionRegistry.registerBeanDefinition("monkey", rootBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}

```





修改服务类

```java
package com.example.sb2.ioc.xml;

//public class HelloService {
//    private Student student;
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public  String hello() {
//        return student.toString();
//    }
//}


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HelloService {
    private Student student;

    @Autowired
    @Qualifier("monkey")
    private Animal animal;


    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String hello() {
//        return student.toString();
        return "hello";
    }


    public String hello2() {
        return animal.getName();
    }
}
```



## 实现ImportBeanDefinitionRegistry

```
package com.example.sb2.ioc.xml;

public class Bird extends Animal{
    @Override
    String getName() {
        return "bird";
    }
}
```







```
package com.example.sb2.ioc.ann;

import com.example.sb2.ioc.xml.Bird;
import com.example.sb2.ioc.xml.Monkey;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyBeanImport implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(Bird.class);
        registry.registerBeanDefinition("bird", rootBeanDefinition);
    }
}
```



```
package com.example.sb2.ioc.xml;

//public class HelloService {
//    private Student student;
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public  String hello() {
//        return student.toString();
//    }
//}


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HelloService {
    private Student student;

    @Autowired
    @Qualifier("bird")
    private Animal animal;


    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String hello() {
//        return student.toString();
        return "hello";
    }


    public String hello2() {
        return animal.getName();
    }
}
```





修改测试类

```java
package com.example.sb2;

import com.example.sb2.ioc.ann.MyBeanImport;
import com.example.sb2.ioc.xml.HelloService;
import com.example.sb2.event.WeatherRunListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Import(MyBeanImport.class)
//@ContextConfiguration(locations = "classpath:ioc/demo.xml")
public class Sb2ApplicationTests {

    @Autowired
    private HelloService helloService;


    @Test
    public void testHello() {
        System.out.println(helloService.hello());
        System.out.println(helloService.hello2());
    }

// @Autowired
// private WeatherRunListener weatherRunListener;

//
// @Before
// public void init() {
//    System.out.println("开始测试-----------------");
// }
//
// @After
// public void after() {
//    System.out.println("测试结束-----------------");
// }
//
//
// @Test
// public void testEvent() {
//    weatherRunListener.rain();
//    weatherRunListener.snow();
// }


}
```

优点：

* 使用简单
* 开发效率高
* 高内聚

缺点

* 配置分散
* 对象关系不清晰
* 配置修改需要重新编译工程