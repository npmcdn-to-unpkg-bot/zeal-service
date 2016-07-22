package com.zeal.service.impl;

import com.zeal.dao.UserInfoDao;
import com.zeal.entity.UserInfo;
import com.zeal.exception.BizException;
import com.zeal.exception.BizExceptionCode;
import com.zeal.http.request.user.*;
import com.zeal.http.response.album.AlbumAuthorInfo;
import com.zeal.service.UserInfoService;
import com.zeal.utils.ConfigureUtils;
import com.zeal.utils.ImageUtils;
import com.zeal.utils.StringUtils;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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


    /**
     * 更新用户的基本信息
     *
     * @param userInfoId
     * @param request
     */
    @Override
    @Transactional
    public void updateBasicUserInfo(long userInfoId, UpdateBasicUserInfoRequest request) {
        if (request == null || StringUtils.isEmpty(request.getNickName())
                || StringUtils.isEmpty(request.getEmail())
                || StringUtils.isEmpty(request.getPhoneNumber())) {
            throw new BizException("错误的请求参数");
        }

        List<UserInfo> users = userInfoDao.findByPhoneNumberEquals(request.getPhoneNumber());
        if (users.size() > 1 || (users.size() == 1 && users.get(0).getId() != userInfoId)) {
            throw new BizException("手机号码已经存在");
        }
        users = userInfoDao.findByEmailEquals(request.getEmail());
        if (users.size() > 1 || (users.size() == 1 && users.get(0).getId() != userInfoId)) {
            throw new BizException("邮箱已经存在已经存在");
        }
        UserInfo userInfo = userInfoDao.find(userInfoId);
        userInfo.setNickName(request.getNickName());
        userInfo.setDescription(request.getDescription());
        userInfo.setEmail(request.getEmail());
        userInfo.setPhoneNumber(request.getPhoneNumber());
        userInfoDao.update(userInfo);
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordRequest request, long userInfoId) {
        if (request == null || StringUtils.isEmpty(request.getPassword()) || StringUtils.isEmpty(request.getNewPassword())) {
            throw new BizException("错误的请求参数");
        }
        UserInfo userInfo = userInfoDao.find(userInfoId);
        String oldPassword = StringUtils.toMD5(request.getPassword());
        if (oldPassword == null || !oldPassword.equals(userInfo.getPassword())) {
            throw new BizException("原始密码不正确");
        }
        userInfo.setPassword(StringUtils.toMD5(request.getNewPassword()));
        userInfoDao.update(userInfo);
    }

    @Override
    @Transactional
    public void updatePhoto(UpdatePhotoRequest request, long userInfoId) throws IOException {
        if (request == null || StringUtils.isEmpty(request.getPhotoBase64())) {
            throw new BizException("错误的请求参数");
        }
        UserInfo userInfo = userInfoDao.find(userInfoId);
        String base64Str = request.getPhotoBase64().substring(request.getPhotoBase64().indexOf(",") + 1);
        if (!ImageUtils.isImage(base64Str)) {
            throw new BizException("字符串不是base64编码的图片");
        }
        String type = ImageUtils.getImageType(base64Str);
        if (type == null) {
            throw new BizException("无法获取上传的图片类型");
        }
        String relatePath = userInfo.getId() + File.separator + "photos" + File.separator + UUID.randomUUID() + "." + type;
        String savePath = ConfigureUtils.getPhotoRepository() + relatePath;
        ImageUtils.base64ToImage(base64Str, savePath);
        userInfo.setPhoto(relatePath);
        userInfoDao.update(userInfo);
    }
}
