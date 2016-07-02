package com.zeal.vo.album;

import com.zeal.entity.Picture;
import com.zeal.utils.ConfigureUtils;

/**
 * Created by Administrator on 6/29/2016.
 */
public class PictureVO {

    private long id;

    private String url;

    private String description;

    public PictureVO(Picture picture) {
        if (picture != null) {
            this.id = picture.getId();
            this.url = ConfigureUtils.getAlbumServer() + picture.getId();
            this.description = picture.getDescription();
        }
    }

    public PictureVO() {

    }

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
