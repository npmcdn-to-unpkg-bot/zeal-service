package com.zeal.service.impl;

import com.zeal.dao.PictureDao;
import com.zeal.entity.Picture;
import com.zeal.service.PictureService;
import com.zeal.utils.ConfigureUtils;
import com.zeal.utils.PictureUtils;
import com.zeal.vo.album.AlbumVO;
import com.zeal.vo.album.PictureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * Created by Administrator on 6/29/2016.
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public PictureVO find(long id) {
        Picture picture = pictureDao.find(id);
        if (picture == null) return null;
        return new PictureVO(picture);
    }

    @Override
    @Transactional
    public void delete(long id, long userInfoId) {
        Picture picture = pictureDao.find(id);
        pictureDao.delete(picture);
        PictureUtils.deleteDiskFile(picture);
    }

    @Override
    public long findUserInfoIdByPictureId(long pictureId) {
        Picture picture = pictureDao.find(pictureId);
        return picture.getAlbum().getUserInfo().getId();
    }

    @Override
    public File getDiskFile(long pictureId) {
        Picture picture = pictureDao.find(pictureId);
        return new File(ConfigureUtils.getAlbumRepository() + picture.getUrl());
    }

    @Override
    public AlbumVO findAlbumByPictureId(long pictureId) {
        return new AlbumVO(pictureDao.find(pictureId).getAlbum());
    }

}
