package com.zeal.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 相册收藏记录
 * Created by Administrator on 7/14/2016.
 */
@Entity
@Table(name = "t_album_collection")
public class AlbumCollection extends BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "collect_time", nullable = false)
    private Date collectTime; //收藏的时间

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserInfo userInfo; //收藏者

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Album album; //收藏的相册


    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
