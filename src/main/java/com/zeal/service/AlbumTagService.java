package com.zeal.service;

import com.zeal.vo.album.AlbumTagVO;

import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/8.
 */
public interface AlbumTagService {

    /**
     * 获取所有子TAG
     *
     * @param parentId
     * @return
     */
    List<AlbumTagVO> findChildrenByTagId(long parentId);

    List<AlbumTagVO> findAllChildren();

}
