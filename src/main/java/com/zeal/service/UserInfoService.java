package com.zeal.service;

import com.zeal.entity.UserInfo;

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
     * 根据用户登录名和密码查找用户信息
     *
     * @param loginName 用户登录名
     * @param password  用户密码（未加密）
     * @return 用户信息
     */
    UserInfo findByLoginNameAndPasswordEquals(String loginName, String password);


}
