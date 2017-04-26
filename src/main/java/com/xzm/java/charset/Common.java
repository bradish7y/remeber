package com.xzm.java.charset;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by Bradish7Y on 16/2/15.
 */
public class Common { 
    public static void main(String[] args) {
        String s = "111";
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer byteBuffer = cs.encode(s);
        CharBuffer charBuffer = cs.decode(byteBuffer);
        System.out.println(charBuffer.toString());
    }

    public static void readFileForInputStream(String fileName) throws IOException {
        Charset cs = Charset.forName("UTF-8");
        InputStream is = new FileInputStream(cs.decode(ByteBuffer.wrap(fileName.getBytes("utf-8"))).toString());

        for(;;) {
            byte[] buffer = new byte[1024];
            int len = is.read(buffer);
            if (len <= 0) {
                return;
            }
        }
    }
}
