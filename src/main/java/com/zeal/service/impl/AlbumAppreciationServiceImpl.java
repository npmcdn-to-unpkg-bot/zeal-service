package com.zeal.service.impl;

import com.zeal.dao.AlbumAppreciationDao;
import com.zeal.dao.AlbumDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.entity.Album;
import com.zeal.entity.AlbumAppreciation;
import com.zeal.entity.UserInfo;
import com.zeal.service.AlbumAppreciationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by yang_shoulai on 2016/7/16.
 */
@Service
public class AlbumAppreciationServiceImpl implements AlbumAppreciationService {


    @Autowired
    private AlbumAppreciationDao albumAppreciationDao;

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 创建一条点赞记录
     *
     * @param userInfoId 点赞者ID
     * @param albumId    相册ID
     */
    @Override
    @Transactional
    public void create(long userInfoId, long albumId) {
        if (albumAppreciationDao.findByUserInfoIdAndAlbumId(userInfoId, albumId).isEmpty()) {
            Album album = albumDao.find(albumId);
            UserInfo userInfo = userInfoDao.find(userInfoId);
            AlbumAppreciation appreciation = new AlbumAppreciation();
            appreciation.setUserInfo(userInfo);
            appreciation.setAlbum(album);
            appreciation.setTime(new Date());
            albumAppreciationDao.insert(appreciation);
        }
    }

    /**
     * 删除点赞记录
     *
     * @param userInfoId 点赞者ID
     * @param albumId    相册ID
     */
    @Override
    public void delete(long userInfoId, long albumId) {
        albumAppreciationDao.deleteByUserInfoAndAlbumId(userInfoId, albumId);
    }

    /**
     * 删除点赞记录
     *
     * @param appreciationId 记录ID
     */
    @Override
    @Transactional
    public void delete(long appreciationId) {
        albumAppreciationDao.delete(appreciationId);
    }
}
