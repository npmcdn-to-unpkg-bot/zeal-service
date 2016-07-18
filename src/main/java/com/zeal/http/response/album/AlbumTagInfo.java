package com.zeal.http.response.album;

/**
 * Created by Administrator on 7/15/2016.
 */
public class AlbumTagInfo {

    /**
     * 相册TAG ID
     */
    private long id;

    /**
     * 相册TAG名称
     */
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
