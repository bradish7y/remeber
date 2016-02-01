package com.xzm.pattern.builder;

/**
 * Created by Bradish7Y on 15/6/13.
 */
public class Person {
    private String name ;
    private String age ;
    private String city ;

    private Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.city = builder.city;
    }

    public static class Builder{
        private String name ;
        private String age ;
        private String city ;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(String age) {
            this.age = age;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Person build(){
            return new Person(this) ;
        }
    }
    public String getName() {
        return name;
    }



    public String getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }
}
