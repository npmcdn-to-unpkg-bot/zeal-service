package com.zeal.service;

import com.zeal.entity.UserInfo;
import com.zeal.http.request.user.UserLoginRequest;
import com.zeal.http.request.user.UserRegisterRequest;

/**
 * Created by yang_shoulai on 2016/6/27.
 */
public interface UserInfoService {

    /**
     * 根据用户ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserInfo find(long id);

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求
     * @return 用户信息
     */
    UserInfo login(UserLoginRequest userLoginRequest);

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册信息
     * @return 用户信息
     */
    UserInfo register(UserRegisterRequest userRegisterRequest);
}
