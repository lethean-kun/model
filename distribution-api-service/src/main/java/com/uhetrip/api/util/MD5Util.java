package com.uhetrip.api.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class MD5Util {

    public static String md5(String str, String charset) {
        return DigestUtils.md5Hex(getContentBytes(str, charset));
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (StringUtils.isBlank(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("未知的字符集编码:" + charset);
        }
    }

}
