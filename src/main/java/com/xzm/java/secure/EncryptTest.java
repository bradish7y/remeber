package com.xzm.java.secure;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.JCEKeyGenerator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Bradish7Y on 16/1/11.
 * 各种加密解密测试
 *
 */
public class EncryptTest {
    public static void main(String[] args) throws Exception {

        System.out.println(getSHA1Digest("1".getBytes("utf-8")));
        System.out.println(DigestUtils.shaHex("1".getBytes("utf-8")).toUpperCase());

    }


    /**
     * SHA-1加密
     * @param sha1s
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSHA1Digest(byte[] sha1s) throws NoSuchAlgorithmException {
        MessageDigest dig = MessageDigest.getInstance("SHA-1");
        byte[] bytes = dig.digest(sha1s);

        return bytesToString(bytes);
    }

    public static String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sb.append("0");
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }
}
