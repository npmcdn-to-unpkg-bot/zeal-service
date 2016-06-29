package com.zeal.entity;

import javax.persistence.*;

/**
 * Created by yang_shoulai on 2016/6/28.
 */
@Entity
@Table(name = "t_picture")
public class Picture extends BaseEntity {

    @Column(name = "description", nullable = true, unique = false, length = 255)
    private String description;

    @Column(name = "url", nullable = false, unique = false, length = 255)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album", nullable = false)
    private Album album;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
