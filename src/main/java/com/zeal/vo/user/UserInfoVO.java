package com.zeal.vo.user;

import com.zeal.entity.UserInfo;
import com.zeal.utils.StringUtils;

/**
 * Created by Administrator on 6/29/2016.
 */
public class UserInfoVO {

    private long id;

    private String nickName;

    private String phoneNumber;

    private String photo;

    private String email;

    private String description;

    public UserInfoVO(UserInfo userInfo) {
        if (userInfo != null) {
            this.id = userInfo.getId();
            this.nickName = userInfo.getNickName();
            this.phoneNumber = userInfo.getPhoneNumber();
            this.description = userInfo.getDescription();
            this.photo = userInfo.getPhoto();
            this.email = userInfo.getEmail();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
