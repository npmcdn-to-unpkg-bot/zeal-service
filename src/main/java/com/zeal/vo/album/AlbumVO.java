package com.zeal.vo.album;

import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import com.zeal.vo.user.UserInfoVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 6/29/2016.
 */
public class AlbumVO {

    private long id;

    private UserInfoVO userInfo;

    private Date createDate;

    private Date updateDate;

    private String description;

    private String name;

    private List<PictureVO> pictures;

    public AlbumVO() {

    }

    public AlbumVO(Album album) {
        if (album != null) {
            this.id = album.getId();
            if (album.getUserInfo() != null) {
                this.userInfo = new UserInfoVO(album.getUserInfo());
            }
            this.createDate = album.getCreateDate();
            this.updateDate = album.getUpdateDate();
            this.description = album.getDescription();
            this.name = album.getName();
            List<Picture> pictures = album.getPictures();
            if (pictures != null) {
                List<PictureVO> pictureVOs = new ArrayList<>();
                for (Picture picture : pictures) {
                    pictureVOs.add(new PictureVO(picture));
                }
                this.pictures = pictureVOs;
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserInfoVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVO userInfo) {
        this.userInfo = userInfo;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PictureVO> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureVO> pictures) {
        this.pictures = pictures;
    }
}
