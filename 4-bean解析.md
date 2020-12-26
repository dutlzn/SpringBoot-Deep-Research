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





# refresh方法

* bean配置读取配加载入口
* spring框架启动流程
* 面试重点



![](/43.png)





启动类

```
package com.example.sb2;

import com.example.sb2.initializer.SecondInitializer;
import com.example.sb2.listener.SecondListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
   }

}
```



run方法 一直点

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
      Banner printedBanner = printBanner(environment);
      context = createApplicationContext();
      exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
            new Class[] { ConfigurableApplicationContext.class }, context);
      prepareContext(context, environment, listeners, applicationArguments, printedBanner);
       // 看这个方法
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



refreshContext



```
private void refreshContext(ConfigurableApplicationContext context) {
   if (this.registerShutdownHook) {
      try {
         context.registerShutdownHook();
      }
      catch (AccessControlException ex) {
         // Not allowed in some environments.
      }
   }
   refresh((ApplicationContext) context);
}
```



https://www.jianshu.com/p/d430c6f07566

https://blog.csdn.net/weixin_43960292/article/details/105385441



refresh 接口 的实现

```java
protected void refresh(ApplicationContext applicationContext) {
    Assert.isInstanceOf(AbstractApplicationContext.class, applicationContext);
    //调用创建的容器applicationContext中的refresh()方法
    ((AbstractApplicationContext) applicationContext).refresh();
}

public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext { 
    @Override
    public void refresh() throws BeansException, IllegalStateException {
        synchronized (this.startupShutdownMonitor) {
            // Prepare this context for refreshing.
            /**
             * 刷新上下文环境
             * 容器状态设置
             * 初始化属性设置
             * 检查必备属性是否存在
             */
            prepareRefresh();

            // Tell the subclass to refresh the internal bean factory.
            /**
             * 设置beanFactory序列化id
             * 获取BeanFactory；默认实现是DefaultListableBeanFactory，在创建容器的时候创建的
             */
            ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

            // Prepare the bean factory for use in this context.
            /**
             * //BeanFactory的预准备工作
             * BeanFactory进行一些设置，比如context的类加载器，BeanPostProcessor和XXXAware自动装配等
             * 设置忽略的自动装配接口，以及注册一些组件
             */
            prepareBeanFactory(beanFactory);

            try {
                // Allows post-processing of the bean factory in context subclasses.
                /**
                 * 提供子类覆盖的额外处理，即子类处理自定义的BeanFactoryPostProcess
                 */
                postProcessBeanFactory(beanFactory);

                // Invoke factory processors registered as beans in the context.
                /**
                 * 激活各种BeanFactory处理器,包括BeanDefinitionRegistryBeanFactoryPostProcessor和普通的BeanFactoryPostProcessor
                 * 执行对应的postProcessBeanDefinitionRegistry方法 和 postProcessBeanFactory方法
                 * 调用BeanDefinitionRegistryBeanFactoryPostProcessor实现向容器内添加bean的定义
                 * 调用BeanFactoryPostProcessor实现向容器内bean的定义添加属性
                 */
                invokeBeanFactoryPostProcessors(beanFactory);

                // Register bean processors that intercept bean creation.
                /**
                 * 注册拦截Bean创建的Bean处理器，即注册BeanPostProcessor，不是BeanFactoryPostProcessor，注意两者的区别
                 * 注意，这里仅仅是排序后注册，并不会执行对应的方法，将在bean的实例化时执行对应的方法
                 */
                registerBeanPostProcessors(beanFactory);

                // Initialize message source for this context.
                /**
                 * 初始化MessageSource组件（做国际化功能；消息绑定，消息解析）；
                 */
                initMessageSource();

                // Initialize event multicaster for this context.
                /**
                 * 初始化上下文事件广播器，并放入applicatioEventMulticaster,如ApplicationEventPublisher
                 */
                initApplicationEventMulticaster();

                // Initialize other special beans in specific context subclasses.
                /**
                 * 子类重写这个方法，在容器刷新的时候可以自定义逻辑；如创建Tomcat，Jetty等WEB服务器
                 */
                onRefresh();

                // Check for listener beans and register them.
                /**
                 * 注册应用的监听器。就是注册实现了ApplicationListener接口的监听器bean，
                 * 这些监听器是注册到ApplicationEventMulticaster中的
                 */
                registerListeners();

                // Instantiate all remaining (non-lazy-init) singletons.
                /**
                 * 初始化所有剩下的非懒加载的单例bean
                 */
                finishBeanFactoryInitialization(beanFactory);

                // Last step: publish corresponding event.
                /**
                 * 完成context的刷新，初始化生命周期处理器，调用LifecycleProcessor的onRefresh()方法，
                 * 并且发布事件（ContextRefreshedEvent）
                 */
                finishRefresh();
            }

            catch (BeansException ex) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Exception encountered during context initialization - " +
                            "cancelling refresh attempt: " + ex);
                }

                // Destroy already created singletons to avoid dangling resources.
                destroyBeans();

                // Reset 'active' flag.
                cancelRefresh(ex);

                // Propagate exception to caller.
                throw ex;
            }

            finally {
                // Reset common introspection caches in Spring's core, since we
                // might not ever need metadata for singleton beans anymore...
                resetCommonCaches();
            }
        }
    }
}
```



## prepareRefresh

```java

	/**
	 * Prepare this context for refreshing, setting its startup date and
	 * active flag as well as performing any initialization of property sources.
	 */
	protected void prepareRefresh() {
		// Switch to active.
        // 记录启动时间，
    	// 将 active 属性设置为 true，closed 属性设置为 false，它们都是 AtomicBoolean 类型
		this.startupDate = System.currentTimeMillis();
		this.closed.set(false);
		this.active.set(true);

		if (logger.isDebugEnabled()) {
			if (logger.isTraceEnabled()) {
				logger.trace("Refreshing " + this);
			}
			else {
				logger.debug("Refreshing " + getDisplayName());
			}
		}

		// Initialize any placeholder property sources in the context environment.
		initPropertySources();

		// Validate that all properties marked as required are resolvable:
		// see ConfigurablePropertyResolver#setRequiredProperties
		getEnvironment().validateRequiredProperties();

		// Store pre-refresh ApplicationListeners...
		if (this.earlyApplicationListeners == null) {
			this.earlyApplicationListeners = new LinkedHashSet<>(this.applicationListeners);
		}
		else {
			// Reset local application listeners to pre-refresh state.
			this.applicationListeners.clear();
			this.applicationListeners.addAll(this.earlyApplicationListeners);
		}

		// Allow for the collection of early ApplicationEvents,
		// to be published once the multicaster is available...
		this.earlyApplicationEvents = new LinkedHashSet<>();
	}
```

1.容器状态的设置。
2.初始化属性的设置。
3.检查必备属性是否存在。



设置必备属性

修改firstInitializer

```JAVA
package com.example.sb2.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.*;


// 定义Spring IOC容器中Bean的执行顺序的优先级，而不是定义Bean的加载顺序
@Order(1)
public class FirstInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 获得环境
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

//        Map<String,Object> map = new HashMap<>();
//        map.put("key1", "value1");
//        // map打包成一个属性
//        MapPropertySource mapPropertySource = new MapPropertySource("firstInitializer", map);
//        environment.getPropertySources().addLast(mapPropertySource);
//        System.out.println("******** Run FirstInitializer ******** ");


//        设置必备属性
        environment.setRequiredProperties("WUDI");
    }
}
```



属性配置文件：

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
```



就可以正常启动了 

获取BeanFactory

##  obtainFreshBeanFactory

```
obtainFreshBeanFactory
```

* 设置beanFactory序列化id
* 获取beanFactory

## prepareBeanFactory

* 设置beanFactory一些属性
* 添加后置处理器
* 设置忽略的自动装配接口
* 注册一些组件

## postProcessBeanFactory

```java
@Override
protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
   super.postProcessBeanFactory(beanFactory);
   if (!ObjectUtils.isEmpty(this.basePackages)) {
      this.scanner.scan(this.basePackages);
   }
   if (!this.annotatedClasses.isEmpty()) {
      this.reader.register(ClassUtils.toClassArray(this.annotatedClasses));
   }
}
```



* 子类重写以在 beanFactory完成创建之后做进一步设置 

  

## invokeBeanFactoryPostProcessors

invokeBeanFactoryPostProcessors步骤：



![](/44.png)





![](/45.png)



![](/46.PNG)





![](/47.PNG)

属性注入



```java
package com.example.sb2.ioc.ann;

import org.springframework.stereotype.Component;

@Component
public class Teacher {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {java
        this.name = name;
    }
}
```





```java
package com.example.sb2.ioc.ann;                                                                                                                               

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostprocessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition teacher = beanFactory.getBeanDefinition("teacher");
        MutablePropertyValues propertyValues = teacher.getPropertyValues();
        propertyValues.addPropertyValue("name" ,"wangwu");
    }
}
```





 

有bug

测试类

```java
package com.example.sb2;

import com.example.sb2.ioc.ann.MyBeanImport;
import com.example.sb2.ioc.ann.Teacher;
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
    public void contextLoads() {

    }

    @Autowired
    private Teacher teacher;
//
//    @Test
//    public void testHello() {
//        System.out.println(helloService.hello());
//        System.out.println(helloService.hello2());
//    }

    @Test
    public void testName() {
        System.out.println(teacher.getName());
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



启动可以输出信息

**invokeBeanFactoryPostProcessors**

* 调用BeanDefinitionRegistryPostProcessor实现向容器内添加bean的定义
* 调用BeanFactoryPostProcessor实现向容器内bean的定义的添加属性

## registerBeanPostProcessors

```
// Register bean processors that intercept bean creation.
registerBeanPostProcessors(beanFactory);
```



* 找到**BeanPostProcessor**的实现
* 排序后注册进容器内



## initMessageSource

* 初始化国际化相关属性

## initApplicationEventMulticaster

* 初始化事件广播器

## onRefresh

空实现，留给子类去继承

* 创建web容器

## registerListeners



* 添加容器内事件监听器至广播器中
* 派发早期事件

## finishBeanFactoryInitialization

参考链接：https://blog.csdn.net/qq_44836294/article/details/107795639



* 初始化所有剩下的单实例Bean

## finishRefresh

* 初始化生命周期处理器
* 调用生命周期处理器onRfresh方法
* 发布ContextRrefreshredEvent事件、
* JMX相关处理

最后完成对上述操作缓存的清理工作



reFresh是spring容器的一个核心方法



# Bean实例化解析

finishBeanFactoryInitialization里面

```
preInstantiateSingletons
```



新建一个worker对象



```java
package com.example.sb2.ioc.ann;

import org.springframework.stereotype.Component;

@Component
public class Worker {
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```



跟踪



## BeanDefinition介绍

* 一个对象在Spring中描述，RootBeanDefinition是其常见实现
* 通过操作BeanDefinition来完成bean实例化和属性注入



## BeanDefinition类图

![](/48.png)



## 实例化流程



![](/49.png)



# 总结

https://www.cnblogs.com/hetutu-5238/p/12394806.html

ioc相比传统模式带来的提效

xml与注解两种配置bean方式的对比

refresh方法流程

bean实例化步骤

# 面试题

介绍一下ioc思想

springboot 中 bean有哪几种配置方式分别介绍一下

bean的配置你最喜欢哪种方式

介绍一下refresh方法流程

请介绍一下refresh中你比较熟悉的方法说出其作用

介绍一下bean实例化的流程

说几个bean实例化的扩展点及其作用

