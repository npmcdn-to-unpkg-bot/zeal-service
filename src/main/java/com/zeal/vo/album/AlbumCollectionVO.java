package com.zeal.vo.album;

import com.zeal.entity.AlbumCollection;
import com.zeal.vo.user.UserInfoVO;

import java.util.Date;

/**
 * Created by Administrator on 7/14/2016.
 */
public class AlbumCollectionVO {

    private long id;

    private Date collectTime;

    private UserInfoVO author;

    private AlbumVO album;

    public AlbumCollectionVO() {
    }

    public AlbumCollectionVO(AlbumCollection albumCollection) {
        this.id = albumCollection.getId();
        this.collectTime = albumCollection.getCollectTime();
        this.author = new UserInfoVO(albumCollection.getUserInfo());
        this.album = new AlbumVO(albumCollection.getAlbum());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }
}
