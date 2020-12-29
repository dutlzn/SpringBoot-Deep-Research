package com.example.sb2.pro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
public class ResultCommandLineRunner implements
        CommandLineRunner, EnvironmentAware ,MyAware{

    private Environment env;

    private Flag flag;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(env.getProperty("sb2.default.name"));
        System.out.println(env.getProperty("sb2.active.name"));
        System.out.println(env.getProperty("sb2.active2.name"));
//        System.out.println(flag.isCanOperate());
//        System.out.println(
//                env.getProperty("sb2.website.url")
//        );
//
//        System.out.println(
//                env.getProperty("sb2.avg.age")
//        );

//        System.out.println(env.getProperty("sb2.website.path"));
    }

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    @Override
    public void setFlag(Flag flag) {
        this.flag = flag;
    }


}
