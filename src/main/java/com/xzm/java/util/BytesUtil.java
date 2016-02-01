package com.xzm.java.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Bradish7Y on 16/1/17.
 */
public class BytesUtil {
    private BytesUtil() {
    }

    /**
     * 16进制字符串到字节
     *
     * @param bytes
     * @return
     * @throws DecoderException
     */
    public static byte[] hex2bytes(byte[] bytes) throws DecoderException {
        return new Hex().decode(bytes);
    }

    public static String bytes2hex01(byte[] bytes) {
        /**
         * 第一个参数的解释，记得一定要设置为1
         *  signum of the number (-1 for negative, 0 for zero, 1 for positive).
         */
        BigInteger bigInteger = new BigInteger(1, bytes);
        return bigInteger.toString(16);
    }

    public static String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位
            {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }

        return sb.toString();

    }

    public static String bytes2hex03(byte[] bytes) {
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt(b & 0x0f));
        }

        return sb.toString();
    }

    /**
     * SHA-1加密
     *
     * @param sha1s
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSHA1Digest(byte[] sha1s) throws NoSuchAlgorithmException {
        MessageDigest dig = MessageDigest.getInstance("SHA-1");
        byte[] bytes = dig.digest(sha1s);

        return bytesToString(bytes);
    }

    /**
     * md5加密
     *
     * @param sha1s
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5Digest(byte[] sha1s) throws NoSuchAlgorithmException {
        MessageDigest dig = MessageDigest.getInstance("md5");
        byte[] bytes = dig.digest(sha1s);
        return bytesToString(bytes);

    }

    /**
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     *
     * @param secretByte
     * @param dataBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    public static String getHmac(byte[] secretByte, byte[] dataBytes, String hmacAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {

        SecretKey secret = new SecretKeySpec(secretByte, hmacAlgorithm);
        Mac mac = Mac.getInstance(secret.getAlgorithm());
        mac.init(secret);

        byte[] bytes = mac.doFinal(dataBytes);

        //return bytesToString(bytes) ;
        byte[] hexB = new Hex().encode(bytes);

        return new String(hexB);
    }

    /**
     * * BASE64解密
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * * BASE64 加密
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    public static void main(String[] args) throws Exception {
        String ret = getHmac("1".getBytes("utf-8"), "1".getBytes("utf-8"), "HmacSHA512");
        System.out.println("ret = " + ret);
    }
}
