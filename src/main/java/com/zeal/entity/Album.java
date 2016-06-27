package com.zeal.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yang_shoulai on 2016/6/28.
 */
@Entity
@Table(name = "album")
public class Album extends BaseEntity {

    @Column(name = "name", nullable = false, unique = false, length = 255)
    private String name;

    @Column(name = "description", nullable = true, unique = false, length = 1000)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date", nullable = false, unique = false)
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_date", nullable = false, unique = false)
    private Date updateDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Picture> pictures;


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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
