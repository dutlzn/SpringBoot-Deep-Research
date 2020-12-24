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
