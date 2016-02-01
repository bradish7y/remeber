package com.xzm.java.util;

import java.nio.ByteBuffer;

/**
 * Created by Bradish7Y on 16/1/19.
 */
public class Byte2Type {
    public static byte[] int2Bytes(int value) {
        byte[] bytes = new byte[4];
        ByteBuffer.wrap(bytes).putInt(value);
        return bytes;
    }

    public static int fromBytes2Int(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] long2Bytes(long value) {
        byte[] bytes = new byte[Long.SIZE];
        ByteBuffer.wrap(bytes).putLong(value);
        return bytes;
    }

    public static long fromBytes2Long(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }

    public static void main(String[] args) {
        System.out.println("long.size=" + Long.SIZE);
        System.out.println("long.max=" + Long.MAX_VALUE);
        System.out.println(BytesUtil.bytes2hex01(long2Bytes(9L)));
        System.out.println(fromBytes2Long(long2Bytes(9L)));
    }
}
