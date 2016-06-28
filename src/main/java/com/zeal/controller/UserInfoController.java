package com.zeal.controller;

import com.zeal.entity.UserInfo;
import com.zeal.http.request.user.UserLoginRequest;
import com.zeal.http.request.user.UserRegisterRequest;
import com.zeal.http.response.Response;
import com.zeal.service.UserInfoService;
import com.zeal.utils.SessionUtils;
import com.zeal.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/userinfo")
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Response post(@PathVariable Long id) {
        return new Response.Builder().success().result(userInfoService.find(id)).build();
    }

    /**
     * 用户登录接口
     *
     * @param userLoginRequest   登录请求参数
     * @param httpServletRequest
     * @return 用户登录成功或者失败信息
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Response login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        UserInfo userInfo = userInfoService.login(userLoginRequest);
        if (userInfo == null) {
            return new Response.Builder().failed().result(null).message("用户不存在，请检查用户名、密码是否正确").build();
        }
        SessionUtils.setAttribute(httpServletRequest, SessionUtils.KEY_USERINFO, userInfo);
        return new Response.Builder().success().result(userInfo).build();
    }

    /**
     * 用戶登出接口
     * 失效用户会话信息
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/logout", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Response logout(HttpServletRequest httpServletRequest) {
        SessionUtils.ivalidate(httpServletRequest);
        return new Response.Builder().success().build();
    }

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册信息
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Response register(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserInfo userInfo = userInfoService.register(userRegisterRequest);
        if (userInfo == null) {
            return new Response.Builder().failed().result(null).message("注册失败").build();
        }
        return new Response.Builder().success().result(userInfo).build();
    }


}
