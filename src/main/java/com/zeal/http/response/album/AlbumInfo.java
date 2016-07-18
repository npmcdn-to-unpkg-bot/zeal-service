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
    private long id;

    /**
     * 相册名称
     */
    private String name;

    /**
     * 相册简介
     */
    private String description;

    /**
     * 是否已经发布
     */
    private boolean published;

    /**
     * 相册被收藏的次数
     */
    private long collectionCount;

    /**
     * 相册被评论次数
     */
    private long commentCount;

    /**
     * 相册被赞的次数
     */
    private long appreciationCount;

    /**
     * 当前用户是否已经收藏该相册
     * 未登陆用户false
     */
    private boolean collected;

    /**
     * 是否点赞该相册
     * 未登陆用户false
     */
    private boolean appreciated;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 发布日期
     */
    private Date publishDate;

    /**
     * 相册图片
     */
    private List<PictureInfo> pictures;

    /**
     * 相册TAG
     */
    private List<AlbumTagInfo> tags;

    /**
     * 作者ID
     */
    private long author;

    /**
     * 作者名称
     */
    private String authorName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public long getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(long collectionCount) {
        this.collectionCount = collectionCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getAppreciationCount() {
        return appreciationCount;
    }

    public void setAppreciationCount(long appreciationCount) {
        this.appreciationCount = appreciationCount;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public boolean isAppreciated() {
        return appreciated;
    }

    public void setAppreciated(boolean appreciated) {
        this.appreciated = appreciated;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public List<PictureInfo> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureInfo> pictures) {
        this.pictures = pictures;
    }

    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<AlbumTagInfo> getTags() {
        return tags;
    }

    public void setTags(List<AlbumTagInfo> tags) {
        this.tags = tags;
    }
}
