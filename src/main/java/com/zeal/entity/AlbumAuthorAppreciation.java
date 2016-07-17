package com.zeal.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户获赞记录
 * Created by yang_shoulai on 2016/7/16.
 */
@Entity
@Table(name = "t_album_author_appreciation")
public class AlbumAuthorAppreciation extends BaseEntity{

    /**
     * 点赞的时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date time;

    /**
     * 点赞者ID
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserInfo appreciator;

    /**
     * 被点赞用户ID
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserInfo appreciated;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public UserInfo getAppreciator() {
        return appreciator;
    }

    public void setAppreciator(UserInfo appreciator) {
        this.appreciator = appreciator;
    }

    public UserInfo getAppreciated() {
        return appreciated;
    }

    public void setAppreciated(UserInfo appreciated) {
        this.appreciated = appreciated;
    }
}
