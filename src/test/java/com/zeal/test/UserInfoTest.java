package com.zeal.test;

import com.zeal.http.request.user.UserRegisterRequest;
import com.zeal.service.UserInfoService;
import com.zeal.vo.user.UserInfoVO;
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
        UserInfoVO userInfo = userInfoService.register(registerRequest);
        Assert.assertTrue(userInfo.getId() != 0);
    }
}
