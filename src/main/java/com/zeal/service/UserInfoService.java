package com.zeal.service;

import com.zeal.http.request.user.UserLoginRequest;
import com.zeal.http.request.user.UserRegisterRequest;
import com.zeal.vo.user.UserInfoVO;

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
    UserInfoVO find(long id);

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求
     * @return 用户信息
     */
    UserInfoVO login(UserLoginRequest userLoginRequest);

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册信息
     * @return 用户信息
     */
    UserInfoVO register(UserRegisterRequest userRegisterRequest);
}
