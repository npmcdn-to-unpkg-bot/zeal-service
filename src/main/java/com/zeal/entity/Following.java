package com.zeal.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yang_shoulai on 2016/7/21.
 */
@Entity
@Table(name = "t_following")
public class Following extends BaseEntity{


    /**
     * 关注的时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date time;


    /**
     * 被关注的人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserInfo followed;

    /**
     * 关注者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserInfo follower;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public UserInfo getFollowed() {
        return followed;
    }

    public void setFollowed(UserInfo followed) {
        this.followed = followed;
    }

    public UserInfo getFollower() {
        return follower;
    }

    public void setFollower(UserInfo follower) {
        this.follower = follower;
    }
}
