package com.xzm.spring.bean;

/**
 * Created by Bradish7Y on 15/11/5.
 */
public class Config {
    private int s ;

    public Config(int s) {
        this.s = s;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Config{");
        sb.append("s=").append(s);
        sb.append('}');
        return sb.toString();
    }
}
