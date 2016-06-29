package com.zeal.exception;

/**
 * Created by Administrator on 6/28/2016.
 */
public class BizExceptionCode {


    public static class User {
        /**
         * 用戶-登录:用户名或者密码为空
         */
        public static final int LOGIN_EMPTY_REQUEST = 100001001;

        /**
         * 用戶-注册：注册信息为空
         */
        public static final int REGISTER_EMPTY_REQUEST = 100002001;

        /**
         * 用戶-注册：登录名为空
         */
        public static final int REGISTER_EMPTY_LOGINNAME = 100002002;

        /**
         * 用戶-注册：密码为空
         */
        public static final int REGISTER_EMPTY_PASSWORD = 100002003;

        /**
         * 用戶-注册：手机号为空
         */
        public static final int REGISTER_EMPTY_PHONENUMBER = 100002004;

        /**
         * 用戶-注册：手机号已经存在
         */
        public static final int REGISTER_EXIST_PHONENUMBER = 100002005;

        /**
         * 用戶-注册：登录名已经存在
         */
        public static final int REGISTER_EXIST_LOGINNAME = 100002006;
        /**
         * 用戶-注册：登录名已经存在
         */
        public static final int REGISTER_WRONG_PHONENUMBER = 100002007;

    }


    public static class System {
        /**
         * 权限不足
         */
        public static final int PERMISSION_INSUFFICIENT = 200001001;
    }

}
