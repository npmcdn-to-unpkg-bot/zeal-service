package com.zeal.http.response.album;

/**
 * Created by Administrator on 7/15/2016.
 */
public class AlbumAuthorInfo {

    /**
     * 作者ID
     */
    private long id;

    /**
     * 作者昵称
     */
    private String nickName;

    /**
     * 作者的邮箱
     */
    private String email;

    /**
     * 作者的头像链接
     */
    private String photo;

    /**
     * 作者简介
     */
    private String description;

    /**
     * 该作者被赞的次数
     */
    private long appreciationCount;

    /**
     * 该作者的关注数量
     */
    private long followingCount;

    /**
     * 该作者被关注的次数
     */
    private long followerCount;

    /**
     * 该作者发布的相册数量
     */
    private long publishedCount;

    /**
     * 当前登录用户是否已经点赞过该作者
     */
    private boolean appreciated;

    /**
     * 当前用户是否已经关注该作者
     */
    private boolean followed;

    /**
     * 未发布的相册数量
     */
    private long unpublishedCount;

    /**
     * 收藏的相册数量
     */
    private long collectionCount;

    /**
     * 我的评论数量
     */
    private long commentCount;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAppreciationCount() {
        return appreciationCount;
    }

    public void setAppreciationCount(long appreciationCount) {
        this.appreciationCount = appreciationCount;
    }

    public long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(long followingCount) {
        this.followingCount = followingCount;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    public long getPublishedCount() {
        return publishedCount;
    }

    public void setPublishedCount(long publishedCount) {
        this.publishedCount = publishedCount;
    }

    public boolean isAppreciated() {
        return appreciated;
    }

    public void setAppreciated(boolean appreciated) {
        this.appreciated = appreciated;
    }

    public long getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(long collectionCount) {
        this.collectionCount = collectionCount;
    }

    public long getUnpublishedCount() {
        return unpublishedCount;
    }

    public void setUnpublishedCount(long unpublishedCount) {
        this.unpublishedCount = unpublishedCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
}
