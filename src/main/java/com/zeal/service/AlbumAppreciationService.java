package com.zeal.service;

/**
 * Created by yang_shoulai on 2016/7/16.
 */
public interface AlbumAppreciationService {

    /**
     * 创建一条点赞记录
     *
     * @param userInfoId 点赞者ID
     * @param albumId    相册ID
     */
    void create(long userInfoId, long albumId);

    /**
     * 删除点赞记录
     *
     * @param userInfoId 点赞者ID
     * @param albumId    相册ID
     */
    void delete(long userInfoId, long albumId);

    /**
     * 删除点赞记录
     *
     * @param appreciationId 记录ID
     */
    void delete(long appreciationId);

}
