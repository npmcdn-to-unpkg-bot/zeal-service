package com.zeal.service.impl;

import com.zeal.dao.AlbumAuthorAppreciationDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.entity.AlbumAuthorAppreciation;
import com.zeal.entity.UserInfo;
import com.zeal.service.AlbumAuthorAppreciationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/16.
 */
@Service
public class AlbumAuthorAppreciationServiceImpl implements AlbumAuthorAppreciationService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private AlbumAuthorAppreciationDao albumAuthorAppreciationDao;

    @Override
    @Transactional
    public void create(long appreciator, long appreciated) {
        if (albumAuthorAppreciationDao.find(appreciator, appreciated).isEmpty()) {
            UserInfo appreciatorUser = userInfoDao.find(appreciator);
            UserInfo appreciatedUser = userInfoDao.find(appreciated);
            AlbumAuthorAppreciation appreciation = new AlbumAuthorAppreciation();
            appreciation.setTime(new Date());
            appreciation.setAppreciated(appreciatedUser);
            appreciation.setAppreciator(appreciatorUser);
            albumAuthorAppreciationDao.insert(appreciation);
        }
    }

    @Override
    @Transactional
    public void delete(long appreciator, long appreciated) {
        albumAuthorAppreciationDao.delete(appreciator, appreciated);
    }

    @Override
    public List<AlbumAuthorAppreciation> find(long appreciator, long appreciated) {
        return albumAuthorAppreciationDao.find(appreciator, appreciated);
    }

    @Transactional
    @Override
    public void delete(long recordId) {
        albumAuthorAppreciationDao.delete(recordId);
    }
}
