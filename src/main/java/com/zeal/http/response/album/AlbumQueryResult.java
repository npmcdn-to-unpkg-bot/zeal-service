package com.zeal.http.response.album;

import com.zeal.vo.album.AlbumVO;

/**
 * Created by Administrator on 7/14/2016.
 */
public class AlbumQueryResult extends AlbumVO {

    /**
     * 被收藏数
     */
    private long collectionCount;

    /**
     * 当前用户是否已经收藏该相册
     */
    private boolean isCollected;


    public long getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(long collectionCount) {
        this.collectionCount = collectionCount;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

}
