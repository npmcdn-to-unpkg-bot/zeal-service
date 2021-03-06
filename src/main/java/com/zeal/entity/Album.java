package com.zeal.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by yang_shoulai on 2016/6/28.
 */
@Entity
@Table(name = "t_album")
public class Album extends BaseEntity {

    @Column(name = "name", nullable = false, unique = false, length = 255)
    private String name;

    @Column(name = "description", nullable = true, unique = false, length = 1000)
    private String description;

    @Column(name = "published", nullable = false)
    private boolean isPublished;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publish_date")
    private Date publishDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
    private List<Picture> pictures;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_album_tags",
            joinColumns = {@JoinColumn(name = "album_id")},
            inverseJoinColumns = {@JoinColumn(name = "album_tag_id")})
    private Set<AlbumTag> albumTags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
    private Set<AlbumCollection> albumCollections;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
    private Set<AlbumAppreciation> albumAppreciations;


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

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setAlbumTags(Set<AlbumTag> albumTags) {
        this.albumTags = albumTags;
    }

    public Set<AlbumTag> getAlbumTags() {
        return albumTags;
    }

    public Set<AlbumCollection> getAlbumCollections() {
        return albumCollections;
    }

    public void setAlbumCollections(Set<AlbumCollection> albumCollections) {
        this.albumCollections = albumCollections;
    }

    public Set<AlbumAppreciation> getAlbumAppreciations() {
        return albumAppreciations;
    }

    public void setAlbumAppreciations(Set<AlbumAppreciation> albumAppreciations) {
        this.albumAppreciations = albumAppreciations;
    }
}
