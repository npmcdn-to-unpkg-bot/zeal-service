package com.zeal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity {

    @Column(name = "login_name",unique = true, nullable = false,length = 255)
    private String loginName;


    @Column(name = "password",unique = false, nullable = false,length = 255)
    private String password;


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
}
