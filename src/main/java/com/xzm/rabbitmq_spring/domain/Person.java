package com.xzm.rabbitmq_spring.domain;

import java.io.Serializable;

/**
 * Created by Bradish7Y on 16/1/5.
 */
public class Person implements Serializable{
    private String name;
    private String age;

    public Person() {
    }

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public String getAge() {
        return age;
    }

    public Person age(String age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age='").append(age).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
