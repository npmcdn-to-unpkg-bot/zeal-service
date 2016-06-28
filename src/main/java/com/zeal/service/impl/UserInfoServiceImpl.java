package com.zeal.service.impl;

import com.zeal.dao.UserInfoDao;
import com.zeal.entity.UserInfo;
import com.zeal.exception.BizException;
import com.zeal.exception.BizExceptionCode;
import com.zeal.http.request.user.UserLoginRequest;
import com.zeal.http.request.user.UserRegisterRequest;
import com.zeal.service.UserInfoService;
import com.zeal.utils.StringUtils;
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
    public UserInfo find(long id) {
        return userInfoDao.find(id);
    }

    @Override
    public UserInfo login(UserLoginRequest userLoginRequest) {
        if (userLoginRequest == null || StringUtils.isEmpty(userLoginRequest.loginName) || StringUtils.isEmpty(userLoginRequest.password)) {
            throw new BizException(BizExceptionCode.User.LOGIN_EMPTY_REQUEST, "登录名或者密码为空");
        }
        return userInfoDao.findByLoginNameAndPasswordEquals(userLoginRequest.loginName, userLoginRequest.password);
    }

    @Override
    public UserInfo register(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BizException(BizExceptionCode.User.REGISTER_EMPTY_REQUEST, "注册信息为空");
        }
        if (StringUtils.isEmpty(userRegisterRequest.loginName)) {
            throw new BizException(BizExceptionCode.User.REGISTER_EMPTY_LOGINNAME, "登录名为空");
        }
        if (StringUtils.isEmpty(userRegisterRequest.password)) {
            throw new BizException(BizExceptionCode.User.REGISTER_EMPTY_PASSWORD, "密码为空");
        }
        if (StringUtils.isEmpty(userRegisterRequest.phoneNumber)) {
            throw new BizException(BizExceptionCode.User.REGISTER_EMPTY_PHONENUMBER, "手机号为空");
        }
        if (!StringUtils.isPhoneNumber(userRegisterRequest.phoneNumber)) {
            throw new BizException(BizExceptionCode.User.REGISTER_WRONG_PHONENUMBER, "手机号格式不正确");
        }
        if (!userInfoDao.findByLoginNameEquals(userRegisterRequest.loginName).isEmpty()) {
            throw new BizException(BizExceptionCode.User.REGISTER_EXIST_LOGINNAME, "登录名已经存在");
        }
        if (!userInfoDao.findByPhoneNumberEquals(userRegisterRequest.phoneNumber).isEmpty()) {
            throw new BizException(BizExceptionCode.User.REGISTER_EXIST_PHONENUMBER, "手机号已经存在");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginName(userRegisterRequest.loginName);
        userInfo.setPassword(userRegisterRequest.password);
        userInfo.setNickName(userRegisterRequest.nickName);
        userInfo.setPhoneNumber(userRegisterRequest.phoneNumber);
        userInfoDao.save(userInfo);
        return userInfo;
    }
}
