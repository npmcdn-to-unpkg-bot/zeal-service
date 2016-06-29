package com.zeal.service.impl;

import com.zeal.common.PagedList;
import com.zeal.dao.AlbumDao;
import com.zeal.dao.PictureDao;
import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import com.zeal.entity.UserInfo;
import com.zeal.exception.BizException;
import com.zeal.exception.BizExceptionCode;
import com.zeal.service.AlbumService;
import com.zeal.vo.album.AlbumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 6/28/2016.
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private PictureDao pictureDao;

    @Override
    public PagedList<AlbumVO> paged(int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = 10;
        PagedList<Album> albumPagedList = albumDao.paged(page, pageSize);
        return convert(albumPagedList);
    }

    @Override
    public AlbumVO find(long id) {
        Album album = albumDao.find(id);
        if (album == null) return null;
        return new AlbumVO(album);
    }

    @Override
    public List<AlbumVO> findAll() {
        List<Album> albums = albumDao.findAll();
        List<AlbumVO> albumVOs = new ArrayList<>();
        if (!albums.isEmpty()) {
            for (Album album : albums) {
                albumVOs.add(new AlbumVO(album));
            }
        }
        return albumVOs;
    }

    @Override
    public void publish(long id, long userId) {
        Album album = albumDao.findByIdAndUserId(id, userId);
        if (album != null) {
            album.setPublished(true);
        } else {
            throw new BizException(BizExceptionCode.System.PERMISSION_INSUFFICIENT, "权限不足");
        }
        albumDao.update(album);
    }

    @Override
    public void unPublish(long id, long userId) {
        Album album = albumDao.findByIdAndUserId(id, userId);
        if (album != null) {
            album.setPublished(false);
        } else {
            throw new BizException(BizExceptionCode.System.PERMISSION_INSUFFICIENT, "权限不足");
        }
        albumDao.update(album);
    }

    @Override
    public PagedList<AlbumVO> published(int page, int pageSize) {
        return convert(albumDao.pagedByPublishStatus(page, pageSize, true));
    }

    @Override
    public PagedList<AlbumVO> pagedByUserInfoId(int page, int pageSize, long userInfoId) {
        return convert(albumDao.pagedByUserInfoId(userInfoId, page, pageSize));
    }

    @Override
    @Transactional
    public void delete(long id, long userInfoId) {
        Album album = albumDao.find(id);
        UserInfo userInfo = album.getUserInfo();
        if (userInfo.getId() != userInfoId) {
            throw new BizException(BizExceptionCode.System.PERMISSION_INSUFFICIENT, "没有权限");
        }
        List<Picture> pictures = album.getPictures();
        if (pictures != null && !pictures.isEmpty()) {
            for (Picture picture : pictures) {
                pictureDao.delete(picture);
            }
        }
        albumDao.delete(album);
    }


    private PagedList<AlbumVO> convert(PagedList<Album> albumPagedList) {
        PagedList<AlbumVO> albumVOPagedList = new PagedList<>();
        albumVOPagedList.setTotalSize(albumPagedList.getTotalSize());
        albumVOPagedList.setPage(albumPagedList.getPage());
        albumVOPagedList.setSize(albumPagedList.getSize());
        List<Album> albums = albumPagedList.getList();
        List<AlbumVO> albumVOs = new ArrayList<>();
        if (albums != null && !albums.isEmpty()) {
            for (Album album : albums) {
                albumVOs.add(new AlbumVO(album));
            }
        }
        albumVOPagedList.setList(albumVOs);
        return albumVOPagedList;
    }
}
