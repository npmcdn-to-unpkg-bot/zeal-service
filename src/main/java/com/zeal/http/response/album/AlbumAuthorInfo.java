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
}
