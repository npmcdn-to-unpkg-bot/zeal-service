package com.zeal.service;

import com.zeal.vo.album.AlbumCollectionVO;

import java.util.List;

/**
 * Created by Administrator on 7/14/2016.
 */
public interface AlbumCollectionService {

    /**
     * 获取用户的所有相册收藏记录
     *
     * @param userInfoId
     * @return
     */
    List<AlbumCollectionVO> findByUserInfoIdEquals(long userInfoId);

    /**
     * 获取用户所有收藏相册的数量
     *
     * @param userInfoId
     * @return
     */
    long countByUserInfoIdEquals(long userInfoId);


    /**
     * 获取相册的所有收藏记录
     *
     * @param albumId
     * @return
     */
    List<AlbumCollectionVO> findByAlbumIdEquals(long albumId);


    /**
     * 获取相册所有收藏的数量
     *
     * @param albumId
     * @return
     */
    long countByAlbumIdEquals(long albumId);

    /**
     * 删除一条收藏信息
     *
     * @param id 收藏记录ID
     */
    void delete(long id);

    /**
     * 根据用户ID和相册ID删除记录
     *
     * @param userId  用户ID
     * @param albumId 相册ID
     */
    void delete(long userId, long albumId);


    /**
     * 添加一条收藏记录
     *
     * @param userId  收藏者ID
     * @param albumId 相册ID
     */
    void create(long userId, long albumId);

    /**
     * 判断用户是否已经收藏指定相册
     *
     * @param userId
     * @param albumId
     * @return
     */
    boolean collected(long userId, long albumId);

}
