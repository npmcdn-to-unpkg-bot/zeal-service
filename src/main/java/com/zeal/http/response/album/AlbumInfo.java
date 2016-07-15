package com.zeal.http.response.album;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 7/15/2016.
 */
public class AlbumInfo {

    /**
     * 相册ID
     */
    public long id;

    /**
     * 相册名称
     */
    public String name;

    /**
     * 相册创建日期
     */
    public Date createDate;

    /**
     * 相册发布日期
     */
    public Date publishDate;

    /**
     * 相册简介
     */
    public String description;

    /**
     * 相册是否发布
     */
    public boolean isPublished;

    /**
     * 相册图片
     */
    public List<PictureInfo> pictures;

    /**
     * 相册Tag
     */
    public List<AlbumTagInfo> tags;

    /**
     * 相册作者的相关信息
     */
    public AlbumAuthorInfo author;

    /**
     * 相册被收藏的次数
     */
    public long collectionCount;

    /**
     * 相册被评论次数
     */
    public long commentCount;

    /**
     * 相册被赞的次数
     */
    public long appreciationCount;

    /**
     * 当前用户是否已经收藏该相册
     * 未登陆用户false
     */
    public boolean collected;

}
