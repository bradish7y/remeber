package com.xzm.java.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Bradish7Y on 16/2/1.
 */
public class NetUtil {
    public static String getLocalIP() throws UnknownHostException {
        InetAddress inet = InetAddress.getLocalHost();
        return inet.getHostAddress();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getLocalIP());
    }
}
