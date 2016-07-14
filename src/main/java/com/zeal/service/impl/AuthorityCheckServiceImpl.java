package com.zeal.service.impl;

import com.zeal.dao.AlbumDao;
import com.zeal.dao.PictureDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import com.zeal.exception.NoAuthorityException;
import com.zeal.service.AuthorityCheckService;
import com.zeal.utils.SessionUtils;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 7/14/2016.
 */
@Service
public class AuthorityCheckServiceImpl implements AuthorityCheckService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private PictureDao pictureDao;

    /**
     * 已经发布的相册，所有人都有访问权限
     * 未发布的相册只有作者有访问权限
     *
     * @param currentUserId 当前登录用户ID
     * @param albumId       指定相册ID
     * @throws NoAuthorityException
     */
    @Override
    public void checkAlbumReadAuthority(long currentUserId, long albumId) throws NoAuthorityException {
        Album album = albumDao.find(albumId);
        if (!album.isPublished() && album.getUserInfo().getId() != currentUserId) {
            throw new NoAuthorityException();
        }
    }

    @Override
    public void checkAlbumReadAuthority(HttpServletRequest request, long albumId) throws NoAuthorityException {
        Album album = albumDao.find(albumId);
        if (!album.isPublished()) {
            UserInfoVO userInfoVO = SessionUtils.getUserInfo(request);
            if (userInfoVO == null || userInfoVO.getId() != albumId) {
                throw new NoAuthorityException();
            }
        }
    }

    /**
     * 只有相册作者才有修改权限
     *
     * @param currentUserId 当前登录用户ID
     * @param albumId       指定相册ID
     * @throws NoAuthorityException
     */
    @Override
    public void checkAlbumModifyAuthority(long currentUserId, long albumId) throws NoAuthorityException {
        Album album = albumDao.find(albumId);
        if (album.getUserInfo().getId() != currentUserId) {
            throw new NoAuthorityException();
        }
    }

    @Override
    public void checkAlbumModifyAuthority(HttpServletRequest request, long albumId) throws NoAuthorityException {
        UserInfoVO userInfoVO = SessionUtils.getUserInfo(request);
        if (userInfoVO != null) {
            Album album = albumDao.find(albumId);
            if (album.getUserInfo().getId() == userInfoVO.getId()) return;
        }
        throw new NoAuthorityException();
    }

    /**
     * 已经发布的相册，所有人都有访问权限
     * 未发布的相册，只有作者才有访问权限
     *
     * @param currentUserId 当前登录用户ID
     * @param pictureId     指定相片ID
     * @throws NoAuthorityException
     */
    @Override
    public void checkPictureReadAuthority(long currentUserId, long pictureId) throws NoAuthorityException {
        Picture picture = pictureDao.find(pictureId);
        Album album = picture.getAlbum();
        if (!album.isPublished()) {
            if (album.getUserInfo().getId() != currentUserId)
                throw new NoAuthorityException();
        }

    }

    @Override
    public void checkPictureReadAuthority(HttpServletRequest request, long pictureId) throws NoAuthorityException {
        Picture picture = pictureDao.find(pictureId);
        Album album = picture.getAlbum();
        if (!album.isPublished()) {
            UserInfoVO userInfo = SessionUtils.getUserInfo(request);
            if (userInfo == null || album.getUserInfo().getId() != userInfo.getId())
                throw new NoAuthorityException();
        }
    }

    /**
     * 只有相册作者才有图片的修改权限
     *
     * @param currentUserId 当前登录用户ID
     * @param pictureId     指定相片ID
     * @throws NoAuthorityException
     */
    @Override
    public void checkPictureModifyAuthority(long currentUserId, long pictureId) throws NoAuthorityException {
        Picture picture = pictureDao.find(pictureId);
        Album album = picture.getAlbum();
        if (album.getUserInfo().getId() != currentUserId)
            throw new NoAuthorityException();
    }

    @Override
    public void checkPictureModifyAuthority(HttpServletRequest request, long pictureId) throws NoAuthorityException {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        if (userInfo != null) {
            Picture picture = pictureDao.find(pictureId);
            Album album = picture.getAlbum();
            if (album.getUserInfo().getId() == userInfo.getId()) return;
        }
        throw new NoAuthorityException();
    }
}
