package com.zeal.service;

import com.zeal.common.PagedList;
import com.zeal.entity.Album;
import com.zeal.vo.album.AlbumVO;

import java.util.List;

/**
 * Created by Administrator on 6/28/2016.
 */
public interface AlbumService {

    PagedList<AlbumVO> paged(int page, int pageSize);

    AlbumVO find(long id);

    List<AlbumVO> findAll();

    void publish(long id, long userId);

    void unPublish(long id, long userId);

    PagedList<AlbumVO> published(int page, int pageSize);

    PagedList<AlbumVO> pagedByUserInfoId(int page, int pageSize, long userInfoId);

    void delete(long id, long userInfoId);
}
