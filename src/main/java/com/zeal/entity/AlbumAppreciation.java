package com.zeal.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 相册获赞记录
 * Created by yang_shoulai on 2016/7/16.
 */
@Entity
@Table(name = "t_album_appreciation")
public class AlbumAppreciation extends BaseEntity{


    /**
     * 点赞的时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date time;

    /**
     * 点赞者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserInfo userInfo;

    /**
     * 被点赞的相册ID
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Album album;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
