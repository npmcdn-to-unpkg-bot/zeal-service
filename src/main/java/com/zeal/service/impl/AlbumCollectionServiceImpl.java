package com.zeal.service.impl;

import com.zeal.dao.AlbumCollectionDao;
import com.zeal.dao.AlbumDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.entity.AlbumCollection;
import com.zeal.service.AlbumCollectionService;
import com.zeal.vo.album.AlbumCollectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 7/14/2016.
 */

@Service
public class AlbumCollectionServiceImpl implements AlbumCollectionService {

    @Autowired
    private AlbumCollectionDao albumCollectionDao;

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private UserInfoDao userInfoDao;


    @Override
    public List<AlbumCollectionVO> findByUserInfoIdEquals(long userInfoId) {
        return convert(albumCollectionDao.findByUserInfoIdEquals(userInfoId));
    }

    @Override
    public long countByUserInfoIdEquals(long userInfoId) {
        return albumCollectionDao.countByUserInfoIdEquals(userInfoId);
    }

    @Override
    public List<AlbumCollectionVO> findByAlbumIdEquals(long albumId) {
        return convert(albumCollectionDao.findByAlbumIdEquals(albumId));
    }

    @Override
    public long countByAlbumIdEquals(long albumId) {
        return albumCollectionDao.countByAlbumIdEquals(albumId);
    }

    @Override
    @Transactional
    public void delete(long id) {
        albumCollectionDao.delete(id);
    }

    @Override
    @Transactional
    public void delete(long userId, long albumId) {
        albumCollectionDao.delete(userId, albumId);
    }

    @Override
    @Transactional
    public void create(long userId, long albumId) {
        if (!collected(userId, albumId)) {
            AlbumCollection albumCollection = new AlbumCollection();
            albumCollection.setAlbum(albumDao.find(albumId));
            albumCollection.setUserInfo(userInfoDao.find(userId));
            albumCollection.setCollectTime(new Date());
            albumCollectionDao.insert(albumCollection);
        }
    }

    @Override
    public boolean collected(long userId, long albumId) {
        return !albumCollectionDao.findByUserInfoIdAndAlbumId(userId, albumId).isEmpty();
    }


    private List<AlbumCollectionVO> convert(List<AlbumCollection> albumCollections) {
        if (albumCollections != null) {
            List<AlbumCollectionVO> list = new ArrayList<>();
            for (AlbumCollection collection : albumCollections) {
                list.add(new AlbumCollectionVO(collection));
            }
            return list;
        }
        return null;
    }
}
