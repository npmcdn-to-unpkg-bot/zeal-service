package com.zeal.activiti.custom;

import com.zeal.service.UserInfoService;
import com.zeal.vo.user.UserInfoVO;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 7/21/2016.
 */
@Service
public class CustomUserEntityManager extends UserEntityManager {

    @Autowired
    private UserInfoService userInfoService;


    @Override
    public UserEntity findUserById(final String userCode) {
        if (userCode == null)
            return null;
        UserEntity userEntity = new UserEntity();
        UserInfoVO userInfoVO = userInfoService.find(Long.valueOf(userCode));
        userEntity.setId(userInfoVO.getId() + "");
        userEntity.setFirstName(userInfoVO.getNickName());
        userEntity.setLastName(userInfoVO.getPhoneNumber());
        userEntity.setRevision(1);
        return userEntity;
    }

    @Override
    public List<Group> findGroupsByUser(final String userCode) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId,
                                                         String key) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId,
                                                        String type) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        throw new RuntimeException("not implement method.");
    }
}
