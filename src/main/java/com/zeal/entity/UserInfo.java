package com.zeal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity {

    @Column(name = "login_name", unique = true, nullable = false, length = 255)
    private String loginName;

    @Column(name = "password", unique = false, nullable = false, length = 255)
    private String password;

    @Column(name = "nick_name", unique = false, nullable = true, length = 255)
    private String nickName;

    @Column(name = "phone_number", unique = true, nullable = false, length = 255)
    private String phoneNumber;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

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
}
