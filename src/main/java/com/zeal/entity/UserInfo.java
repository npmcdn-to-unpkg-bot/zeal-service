package com.zeal.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_user_info")
public class UserInfo extends BaseEntity {

    @Column(name = "password", unique = false, nullable = false, length = 255)
    private String password;

    @Column(name = "nick_name", unique = false, nullable = true, length = 255)
    private String nickName;

    @Column(name = "phone_number", unique = true, nullable = false, length = 255)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Column(name = "photo")
    private String photo;

    /**
     * 用户简介
     */
    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo")
    private Set<AlbumCollection> albumCollections;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo")
    private Set<AlbumAppreciation> albumAppreciations;

    /**
     * 用户点赞记录
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appreciator")
    private Set<AlbumAuthorAppreciation> appreciations;

    /**
     * 用户被赞记录
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appreciated")
    private Set<AlbumAuthorAppreciation> appreciatedRecords;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public Set<AlbumAuthorAppreciation> getAppreciations() {
        return appreciations;
    }

    public void setAppreciations(Set<AlbumAuthorAppreciation> appreciations) {
        this.appreciations = appreciations;
    }

    public Set<AlbumAuthorAppreciation> getAppreciatedRecords() {
        return appreciatedRecords;
    }

    public void setAppreciatedRecords(Set<AlbumAuthorAppreciation> appreciatedRecords) {
        this.appreciatedRecords = appreciatedRecords;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
