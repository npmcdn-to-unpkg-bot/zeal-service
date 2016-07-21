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

    private UserInfoVO collector;

    private AlbumVO album;

    public AlbumCollectionVO() {
    }

    public AlbumCollectionVO(AlbumCollection albumCollection) {
        this.id = albumCollection.getId();
        this.collectTime = albumCollection.getCollectTime();
        this.collector = new UserInfoVO(albumCollection.getUserInfo());
        this.album = new AlbumVO(albumCollection.getAlbum());
    }

    public UserInfoVO getCollector() {
        return collector;
    }

    public void setCollector(UserInfoVO collector) {
        this.collector = collector;
    }

    public AlbumVO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumVO album) {
        this.album = album;
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
