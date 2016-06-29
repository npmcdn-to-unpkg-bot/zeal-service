package com.zeal.service.impl;

import com.zeal.dao.PictureDao;
import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import com.zeal.entity.UserInfo;
import com.zeal.exception.BizException;
import com.zeal.exception.BizExceptionCode;
import com.zeal.service.PictureService;
import com.zeal.vo.album.PictureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Album album = picture.getAlbum();
        UserInfo userInfo = album.getUserInfo();
        if (userInfo.getId() != userInfoId) {
            throw new BizException(BizExceptionCode.System.PERMISSION_INSUFFICIENT, "没有权限");
        }
        pictureDao.delete(picture);
    }
}
