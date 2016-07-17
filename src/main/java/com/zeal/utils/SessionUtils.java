package com.zeal.utils;

import com.zeal.vo.user.UserInfoVO;

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

    public static void removeAttribute(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }

    public static void setUserInfo(HttpServletRequest request, UserInfoVO userInfoVO) {
        setAttribute(request, KEY_USERINFO, userInfoVO);
    }

    public static UserInfoVO getUserInfo(HttpServletRequest request) {
        return (UserInfoVO) getAttribute(request, KEY_USERINFO);
    }

    public static long getUserInfoId(HttpServletRequest request) {
        UserInfoVO userInfo = getUserInfo(request);
        return userInfo == null ? -1L : userInfo.getId();
    }

    public static void invalidate(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
