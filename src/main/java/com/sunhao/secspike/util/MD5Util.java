package com.sunhao.secspike.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/4/7  14:17
 */
public class MD5Util {

    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }
}
