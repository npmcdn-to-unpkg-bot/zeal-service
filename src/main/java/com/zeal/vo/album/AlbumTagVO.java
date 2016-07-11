package com.zeal.vo.album;

import com.zeal.entity.AlbumTag;

/**
 * Created by yang_shoulai on 2016/7/8.
 */
public class AlbumTagVO {

    private long id;

    private String name;

    private String description;

    private long parent;


    public AlbumTagVO(AlbumTag albumTag) {
        this.id = albumTag.getId();
        this.name = albumTag.getName();
        this.description = albumTag.getDescription();
        this.parent = albumTag.getParent();
    }

    public AlbumTagVO() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }
}
