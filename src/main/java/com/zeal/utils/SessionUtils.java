package com.zeal.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yang_shoulai on 2016/6/27.
 */
public class SessionUtils {

    /**
     * 用户会话用户信息key
     */
    public static final String KEY_USERINFO = "key_userinfo";

    public static void setAttribute(HttpServletRequest request, String key, Object obj) {
        request.getSession().setAttribute(key, obj);
    }

    public static Object getAttribute(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

}
