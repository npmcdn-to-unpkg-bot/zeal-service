package com.zeal.service;

import com.zeal.http.request.user.*;
import com.zeal.http.response.album.AlbumAuthorInfo;
import com.zeal.vo.user.UserInfoVO;

import java.io.IOException;

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


    /**
     * 获取相册作者的相关信息
     *
     * @param id            作者ID
     * @param currentUserId 当前登录用户ID
     * @return
     */
    AlbumAuthorInfo author(long id, long currentUserId);

    /**
     * 更新用户的基本信息
     *
     * @param userInfoId
     * @param request
     */
    void updateBasicUserInfo(long userInfoId, UpdateBasicUserInfoRequest request);

    /**
     * 修改用户密码
     *
     * @param request    新密码请求
     * @param userInfoId 用户ID
     */
    void updatePassword(UpdatePasswordRequest request, long userInfoId);

    /**
     * 修改头像
     *
     * @param request    头像请求数据
     * @param userInfoId 用户ID
     */
    void updatePhoto(UpdatePhotoRequest request, long userInfoId) throws IOException;
}
