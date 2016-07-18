package com.zeal.http.response.album;

/**
 * Created by Administrator on 7/15/2016.
 */
public class PictureInfo {

    /**
     * 图片ID
     */
    private long id;

    /**
     * 图片链接地址
     */
    private String url;

    /**
     * 图片说明
     */
    private String description;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
