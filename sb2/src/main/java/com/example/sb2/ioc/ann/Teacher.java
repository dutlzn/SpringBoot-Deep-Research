package com.example.sb2.ioc.ann;

import org.springframework.stereotype.Component;


@Component
public class Teacher {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
