package com.zeal.http.response.my;

import com.zeal.http.response.album.AlbumAuthorInfo;

/**
 * Created by yang_shoulai on 2016/7/15.
 */
public class MyAlbumAuthorInfo extends AlbumAuthorInfo {

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
}
