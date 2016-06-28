package com.zeal.dao;

import com.zeal.entity.UserInfo;
import com.zeal.utils.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Administrator on 6/27/2016.
 */
@Repository
public class UserInfoDao extends AbstractBaseDao<UserInfo> {

    /**
     * 根据用户登录名和密码查找用户信息
     *
     * @param loginName 用户登录名
     * @param password  用户密码（未加密）
     * @return 用户信息
     */
    public UserInfo findByLoginNameAndPasswordEquals(String loginName, String password) {
        String sql = "SELECT o FROM UserInfo o WHERE o.loginName = :loginName and o.password = :password";
        TypedQuery<UserInfo> query = this.entityManager().createQuery(sql, UserInfo.class);
        query.setParameter("loginName", loginName);
        query.setParameter("password", password);
        return query.getSingleResult();
    }

    /**
     * 根据登录名称查找用户信息
     *
     * @param loginName 用户登录名
     * @return 用户信息列表
     */
    public List<UserInfo> findByLoginNameEquals(String loginName) {
        String sql = "SELECT o FROM UserInfo o WHERE o.loginName = :loginName";
        TypedQuery<UserInfo> query = this.entityManager().createQuery(sql, UserInfo.class);
        query.setParameter("loginName", loginName);
        return query.getResultList();
    }

    /**
     * 根据登录名称查找用户信息
     *
     * @param phoneNumber 用户手机号
     * @return 用户信息列表
     */
    public List<UserInfo> findByPhoneNumberEquals(String phoneNumber) {
        String sql = "SELECT o FROM UserInfo o WHERE o.phoneNumber = :phoneNumber";
        TypedQuery<UserInfo> query = this.entityManager().createQuery(sql, UserInfo.class);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getResultList();
    }

}
