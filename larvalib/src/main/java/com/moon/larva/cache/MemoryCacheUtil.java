package com.moon.larva.cache;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author: moon
 * created on: 17/10/1 上午10:42
 * description: url控制，（将url转成md5后存储到linkedHashmap）
 */
public class MemoryCacheUtil {


    public static String getKeyFromUrl(String url) {
        String key;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            byte[] m = messageDigest.digest();
            return getString(m);
        } catch (NoSuchAlgorithmException e) {
            key = String.valueOf(url.hashCode());
        }
        return key;
    }
    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();
    }



}
