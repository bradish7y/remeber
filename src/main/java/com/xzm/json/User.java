package com.xzm.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Created by Bradish7Y on 15/8/18.
 */
@JsonRootName("USER")
public class User {
    @JsonProperty(value = "User_name")
    @JsonPropertyOrder(value = "0")
    private String user_name;
    @JsonPropertyOrder(value = "1")
    private Integer age;
    private Date birthday;
    private String email;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("user_name", user_name)
                .append("age", age)
                .append("birthday", birthday)
                .append("email", email)
                .toString();
    }
}