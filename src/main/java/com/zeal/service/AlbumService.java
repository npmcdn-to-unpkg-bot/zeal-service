package com.zeal.service;

import com.zeal.common.PagedList;
import com.zeal.entity.Album;
import com.zeal.entity.UserInfo;
import com.zeal.vo.album.AlbumVO;
import com.zeal.worker.albums.PageAlbum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    PagedList<AlbumVO> published(int page, int pageSize, long tagId);

    PagedList<AlbumVO> pagedByUserInfoId(int page, int pageSize, long userInfoId);

    PagedList<AlbumVO> pagedByUserInfoIdAndKeywordLike(int page, int pageSize, long userInfoId, String keyword);

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

}
