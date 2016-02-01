package com.xzm.spring.el;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Bradish7Y on 15/7/26.
 */
@Component
public class ELBean {
    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private int randomNumber;

    @Value("#{systemProperties['file.encodingd']?:'ascii'}")
    private String path;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ELBean{");
        sb.append("randomNumber=").append(randomNumber);
        sb.append(", path='").append(path).append('\'');
        sb.append(", classes=").append(classes);
        sb.append('}');
        return sb.toString();
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    @Value("#{{'a','b','c','d'}}")
    private List<String> classes;

    public int getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
