package com.hstba.jingzheng.util;


public class StringUtil {

    public static boolean isNotBlank(String str) {
        return str != null && !"".equals(str);
    }

    public static String filterEmoji(String source) {
        if (StringUtil.isNotBlank(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");  //不可用
        } else {
            return source;
        }
    }
}
