package com.xzm.java.secure;

import com.xzm.java.util.BytesUtil;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Bradish7Y on 16/1/17.
 */
public class AESEncrypt {
    public static void main(String[] args) throws Exception {
        String content = "testrifjeljflkjdsalfjdlasjfljas";

        // 生成key, 16位长
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);//128位二进制
        SecretKey secretKey = kgen.generateKey();

        byte[] key = secretKey.getEncoded();
        //key = "1234567890123456".getBytes() ;//固定的key
        System.out.println("key=" + BytesUtil.bytes2hex01(key));

        System.out.println("加密前：" + content);

        //加密
        byte[] encryptResult = aesEncrypt(key, content);
        String encryptResultStr = BytesUtil.bytes2hex01(encryptResult);
        System.out.println("加密后：" + encryptResultStr);

        //解密
        byte[] decryptResult = aesDecrypt(key, encryptResult);
        System.out.println("解密后：" + new String(decryptResult));


    }

    public static byte[] aesEncrypt(byte[] key, String content) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] aesDecrypt(byte[] key, byte[] content) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
