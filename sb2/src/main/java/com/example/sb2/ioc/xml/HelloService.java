package com.example.sb2.ioc.xml;

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
