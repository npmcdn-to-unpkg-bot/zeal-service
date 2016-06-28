package com.zeal.test;

import com.zeal.entity.UserInfo;
import com.zeal.http.request.user.UserRegisterRequest;
import com.zeal.service.UserInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 6/28/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/applicationContext.xml")
public class UserInfoTest {
    @Resource
    private UserInfoService userInfoService;

    @Test
    public void save() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.loginName = "5864";
        registerRequest.password = "123456";
        registerRequest.phoneNumber = "15195952861";
        UserInfo userInfo = userInfoService.register(registerRequest);
        Assert.assertTrue(userInfo.getId() != 0);
    }
}
