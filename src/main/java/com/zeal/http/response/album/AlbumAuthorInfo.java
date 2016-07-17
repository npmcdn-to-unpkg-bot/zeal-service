package com.zeal.http.response.album;

/**
 * Created by Administrator on 7/15/2016.
 */
public class AlbumAuthorInfo {

    /**
     * 作者ID
     */
    public long id;

    /**
     * 作者昵称
     */
    public String nickName;

    /**
     * 作者的邮箱
     */
    public String email;

    /**
     * 作者的头像链接
     */
    public String photo;

    /**
     * 作者简介
     */
    public String description;

    /**
     * 该作者被赞的次数
     */
    public long appreciationCount;

    /**
     * 该作者的关注数量
     */
    public long followingCount;

    /**
     * 该作者被关注的次数
     */
    public long followerCount;

    /**
     * 该作者发布的相册数量
     */
    public long publishedCount;

    /**
     * 当前登录用户是否已经点赞过该作者
     */
    public boolean appreciated;


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
}
