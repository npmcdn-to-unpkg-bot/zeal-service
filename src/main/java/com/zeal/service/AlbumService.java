package com.zeal.service;

import com.zeal.common.PagedList;
import com.zeal.entity.Album;
import com.zeal.entity.UserInfo;
import com.zeal.http.response.album.AlbumInfo;
import com.zeal.vo.album.AlbumVO;
import com.zeal.worker.albums.PageAlbum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 6/28/2016.
 */
public interface AlbumService {

    /**
     * 获取相册的详细信息
     *
     * @param id            相册ID
     * @param currentUserId 当前用登录户ID
     * @return
     */
    AlbumInfo findDetails(long id, long currentUserId);

    /**
     * 获取相册几本信息
     *
     * @param id 相册ID
     * @return
     */
    AlbumVO findBasic(long id);


    void publish(long id, long userId);

    void unPublish(long id, long userId);

    /**
     * 分页获取相册信息
     *
     * @param page          分页页码
     * @param pageSize      每页数量
     * @param tagId         相册tag
     * @param currentUserId 当前登录用户ID
     * @return
     */
    PagedList<AlbumInfo> published(int page, int pageSize, long tagId, long currentUserId);

    /**
     * 分页获取相册作者的相册信息
     *
     * @param page          当前页码
     * @param pageSize      每页数量
     * @param authorId      相册作者ID
     * @param keyword       关键字
     * @param currentUserId 当前登录用户ID
     * @param state         相册发布状态 -1，全部；已发布 0； 未发布 1
     * @return
     */
    PagedList<AlbumInfo> pagedByUserInfoIdAndKeywordLike(int page, int pageSize, long authorId, String keyword, long currentUserId, int state);

    void delete(long id, long userInfoId);

    void create(String name, String description, List<MultipartFile> files, long userInfoId);

    /**
     * 更新相册信息
     *
     * @param id            相册ID
     * @param name          相册名称
     * @param description   相册说明
     * @param deleteIdArray 需要删除的图片列表
     * @param newFiles      需要添加的图片
     * @param tags          tags
     * @param userInfoId    用户ID
     */
    void update(long id, String name, String description, int[] deleteIdArray, List<MultipartFile> newFiles, int[] tags, long userInfoId);


    /**
     * 获取相册所在的硬盘文件夹
     *
     * @param album
     * @return
     */
    File getDiskFolder(Album album);

    /**
     * 获取相册所在的硬盘文件夹
     *
     * @param albumId
     * @return
     */
    File getDiskFolder(long albumId);


    void save(PageAlbum pageAlbum, UserInfo userInfo);

    /**
     * 创建相册的缩略图
     *
     * @param albumId
     * @return
     */
    File createThumbnail(long albumId);

    /**
     * 获取相册的缩略图
     *
     * @param albumId
     * @return
     */
    File getThumbnail(long albumId);


    /**
     * 获取用户的发布或者未发布相册的数量
     *
     * @param published
     * @param userId
     * @return
     */
    long countByPublishStatus(boolean published, long userId);

    /**
     * 获取用户发布或者未发布的相册列表
     *
     * @param published
     * @param userId
     * @return
     */
    PagedList<AlbumVO> findByPublishStatus(boolean published, long userId, int page, int pageSize);

}
