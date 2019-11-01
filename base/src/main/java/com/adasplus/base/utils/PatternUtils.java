package com.adasplus.base.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author:刘净辉
 * Date : 2019/9/27 17:28
 * Description :  工具类的作用：本类是为了校验输入的 ip 、手机号 、掩码 等是否合法
 */
public class PatternUtils {

    private static final String PHONE_NUMBER_REG = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$";

    private static final String IP_REGEX = "^([1-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))(\\.([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))){3}$";

    private static final String VEHICLE_NUMBER = "(^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z][A-Z][A-Z0-9]{4}[A-Z0-9挂学警港澳]$)";

    public static boolean checkPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REG);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


    public static boolean checkIpAddress(String ipAddress) {
        Pattern pattern = Pattern.compile(IP_REGEX);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    public static String idFormat(String id) {
        StringBuilder sb = new StringBuilder(id);
        sb.replace(6, 14, "********");
        return sb.toString();
    }

    public static boolean isVehicleNumber(String vehicleNumber) {
        Pattern pattern = Pattern.compile(VEHICLE_NUMBER);
        Matcher matcher = pattern.matcher(vehicleNumber);
        return matcher.matches();
    }
}
