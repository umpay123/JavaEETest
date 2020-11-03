package org.sang.util;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: tanlin
 * @Date: 2020/11/02/18:41
 * @Description:
 */
public class Md5Utils {
    public Md5Utils() {
    }

    public static String digestByMd5(String data) {
        try {
            return DigestUtils.md5Hex(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException("Md5Utils exception: data=" + data);
        }
    }

    public static String digestByMd5(String data, String charset) {
        try {
            return DigestUtils.md5Hex(data.getBytes(charset));
        } catch (UnsupportedEncodingException var3) {
            throw new RuntimeException("Md5Utils exception: data=" + data);
        }
    }
}
