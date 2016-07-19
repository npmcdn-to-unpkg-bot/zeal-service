package com.zeal.service.impl;

import com.zeal.dao.UserInfoDao;
import com.zeal.entity.UserInfo;
import com.zeal.exception.BizException;
import com.zeal.exception.BizExceptionCode;
import com.zeal.http.request.user.UserLoginRequest;
import com.zeal.http.request.user.UserRegisterRequest;
import com.zeal.http.response.album.AlbumAuthorInfo;
import com.zeal.service.UserInfoService;
import com.zeal.utils.StringUtils;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yang_shoulai on 2016/6/27.
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfoVO find(long id) {
        UserInfo userInfo = userInfoDao.find(id);
        if (userInfo == null) return null;
        return new UserInfoVO(userInfo);
    }

    @Override
    public UserInfoVO login(UserLoginRequest userLoginRequest) {
        if (userLoginRequest == null || StringUtils.isEmpty(userLoginRequest.loginName) || StringUtils.isEmpty(userLoginRequest.password)) {
            throw new BizException(BizExceptionCode.User.LOGIN_EMPTY_REQUEST, "登录名或者密码为空");
        }
        UserInfo userInfo = userInfoDao.findByLoginNameAndPasswordEquals(userLoginRequest.loginName, StringUtils.toMD5(userLoginRequest.password));
        if (userInfo == null) return null;
        return new UserInfoVO(userInfo);
    }

    @Override
    public UserInfoVO register(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BizException(BizExceptionCode.User.REGISTER_EMPTY_REQUEST, "注册信息为空");
        }
        if (StringUtils.isEmpty(userRegisterRequest.getPassword())) {
            throw new BizException(BizExceptionCode.User.REGISTER_EMPTY_PASSWORD, "密码为空");
        }
        if (StringUtils.isEmpty(userRegisterRequest.getPhoneNumber())) {
            throw new BizException(BizExceptionCode.User.REGISTER_EMPTY_PHONENUMBER, "手机号为空");
        }
        if (!StringUtils.isPhoneNumber(userRegisterRequest.getPhoneNumber())) {
            throw new BizException(BizExceptionCode.User.REGISTER_WRONG_PHONENUMBER, "手机号格式不正确");
        }
        if (!userInfoDao.findByPhoneNumberEquals(userRegisterRequest.getPhoneNumber()).isEmpty()) {
            throw new BizException(BizExceptionCode.User.REGISTER_EXIST_PHONENUMBER, "手机号已经存在");
        }
        if (StringUtils.isEmpty(userRegisterRequest.getEmail())) {
            throw new BizException("邮箱为空");
        }
        if (!StringUtils.isEmail(userRegisterRequest.getEmail())) {
            throw new BizException("邮箱格式不正确");
        }
        if (userInfoDao.emailExist(userRegisterRequest.getEmail())) {
            throw new BizException("邮箱已经存在");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword(StringUtils.toMD5(userRegisterRequest.getPassword()));
        userInfo.setNickName(userRegisterRequest.getNickName());
        userInfo.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        userInfo.setEmail(userRegisterRequest.getEmail());
        userInfoDao.insert(userInfo);
        return new UserInfoVO(userInfo);
    }

    /**
     * 获取相册作者的相关信息
     *
     * @param id            作者ID
     * @param currentUserId 当前登录用户ID
     * @return
     */
    @Override
    public AlbumAuthorInfo author(long id, long currentUserId) {
        return userInfoDao.author(id, currentUserId);
    }
}
