package com.zeal.service.impl;

import com.zeal.dao.UserInfoDao;
import com.zeal.entity.UserInfo;
import com.zeal.exception.BizException;
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
    public UserInfo findByLoginNameAndPasswordEquals(String loginName, String password) {
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            throw new BizException("登录名或者密码为空");
        }
        return userInfoDao.findByLoginNameAndPasswordEquals(loginName, password);
    }
}
