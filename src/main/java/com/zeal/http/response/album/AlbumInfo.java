package com.zeal.http.response.album;

import com.zeal.vo.album.AlbumVO;

/**
 * Created by Administrator on 7/15/2016.
 */
public class AlbumInfo extends AlbumVO {

    /**
     * 相册被收藏的次数
     */
    private long collectionCount;

    /**
     * 相册被评论次数
     */
    private long commentCount;

    /**
     * 相册被赞的次数
     */
    private long appreciationCount;

    /**
     * 当前用户是否已经收藏该相册
     * 未登陆用户false
     */
    private boolean collected;

    /**
     * 是否点赞该相册
     * 未登陆用户false
     */
    private boolean appreciated;


    public long getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(long collectionCount) {
        this.collectionCount = collectionCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getAppreciationCount() {
        return appreciationCount;
    }

    public void setAppreciationCount(long appreciationCount) {
        this.appreciationCount = appreciationCount;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public boolean isAppreciated() {
        return appreciated;
    }

    public void setAppreciated(boolean appreciated) {
        this.appreciated = appreciated;
    }
}
