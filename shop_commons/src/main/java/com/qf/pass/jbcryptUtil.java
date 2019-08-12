package com.qf.pass;

import org.mindrot.jbcrypt.BCrypt;

/**
 * linzebin
 * 时间2019/7/19 21:36
 */
public class jbcryptUtil {
    // md5加盐加密
    public static String hashPassword(String  password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }
    public static boolean cheackPassword(String hash,String password){
        return BCrypt.checkpw(password,hash);
    }

}
