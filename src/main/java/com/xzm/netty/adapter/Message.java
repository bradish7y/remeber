package com.xzm.netty.adapter;

/**
 * Created by Bradish7Y on 15/5/27.
 */
public class Message {

    private int length;
    private String body;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "length=" + length +
                ", body='" + body + '\'' +
                '}';
    }

    public void clear() {
        length = 0;
        body = "";
    }


}
