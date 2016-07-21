package com.zeal.utils;

import java.security.MessageDigest;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yang_shoulai on 2015/10/12.
 */
public class StringUtils {

    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }


    public static String toMD5(String source) {
        try {
            byte[] btInput = source.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断号码是不是手机号
     *
     * @param number
     * @return
     */
    public static boolean isPhoneNumber(String number) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(number);
        return m.matches();
    }


    public static void main(String[] args) {
        System.out.println(isEmail("412837184@qq.com"));
    }

    /**
     * 判断邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher m = p.matcher(email);
        return m.matches();
    }


    /**
     * 分隔字符串，返回int[]
     *
     * @param str
     * @param splitor
     * @return
     */
    public static int[] splitToIntArray(String str, String splitor) {
        if (isEmpty(str) || splitor == null) throw new IllegalArgumentException();
        String[] strs = str.split(splitor);
        int[] array = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            array[i] = Integer.valueOf(strs[i]);
        }
        return array;
    }

}
