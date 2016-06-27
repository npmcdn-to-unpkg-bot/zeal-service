package com.zeal.dao;

import com.zeal.entity.UserInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Administrator on 6/27/2016.
 */
@Repository
public class UserInfoDao extends AbstractBaseDao<UserInfo>{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    protected EntityManager entityManager() {
        return this.entityManager;
    }
}
