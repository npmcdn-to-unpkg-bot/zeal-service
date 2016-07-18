package com.zeal.http.response.album;

/**
 * Created by Administrator on 7/18/2016.
 */
public class PagedAlbumInfo {

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
     * 相册说略图图片地址
     */
    private String thumbnail;

    /**
     * 作者ID
     */
    private long author;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 作者相册图片
     */
    private String authorPhoto;

    /**
     * 相册被收藏数
     */
    private long collectionCount;

    /**
     * 作者获赞数
     */
    private long authorAppreciationCount;

    /**
     * 当前登录用户是否已经赞过该作者
     */
    private boolean authorAppreciated;

    /**
     * 当前登录用户是否已经收藏该相册
     */
    private boolean collected;


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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public long getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(long collectionCount) {
        this.collectionCount = collectionCount;
    }

    public long getAuthorAppreciationCount() {
        return authorAppreciationCount;
    }

    public void setAuthorAppreciationCount(long authorAppreciationCount) {
        this.authorAppreciationCount = authorAppreciationCount;
    }

    public String getAuthorPhoto() {
        return authorPhoto;
    }

    public void setAuthorPhoto(String authorPhoto) {
        this.authorPhoto = authorPhoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAuthorAppreciated() {
        return authorAppreciated;
    }

    public void setAuthorAppreciated(boolean authorAppreciated) {
        this.authorAppreciated = authorAppreciated;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
