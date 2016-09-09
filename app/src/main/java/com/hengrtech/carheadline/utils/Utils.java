package com.hengrtech.carheadline.utils;

/**
 * 作者：loonggg on 2016/9/8 10:11
 */

public class Utils {
    /**
     * 验证手机号码
     *
     * @param number
     * @return
     */
    public static boolean verifyPhoneNumber(String number) {
        if (number.matches("^(13|15|17|18)\\d{9}$")) {
            return true;
        } else {
            return false;
        }
    }
}
