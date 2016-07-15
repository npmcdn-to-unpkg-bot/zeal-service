package com.zeal.controller;

import com.zeal.http.request.user.UserLoginRequest;
import com.zeal.http.request.user.UserRegisterRequest;
import com.zeal.http.response.Response;
import com.zeal.http.response.my.ZealInfo;
import com.zeal.service.AlbumService;
import com.zeal.service.UserInfoService;
import com.zeal.utils.SessionUtils;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/userinfo")
@Controller
public class UserInfoController extends AbstractController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AlbumService albumService;

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
        UserInfoVO userInfo = userInfoService.login(userLoginRequest);
        if (userInfo == null) {
            return new Response.Builder().failed().result(null).message("用户不存在，请检查用户名、密码是否正确").build();
        }
        SessionUtils.setUserInfo(httpServletRequest, userInfo);
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
        SessionUtils.invalidate(httpServletRequest);
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
        UserInfoVO userInfo = userInfoService.register(userRegisterRequest);
        if (userInfo == null) {
            return new Response.Builder().failed().result(null).message("注册失败").build();
        }
        return new Response.Builder().success().result(userInfo).build();
    }


    @RequestMapping(value = "/reload", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Response reload(HttpServletRequest httpServletRequest) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(httpServletRequest);
        if (userInfo == null) {
            return new Response.Builder().failed().result(null).message("用户已经失效，请从新登录").build();
        }
        return new Response.Builder().success().result(userInfo).build();
    }


    @RequestMapping(value = "/zealInfo/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Response zealInfo(@PathVariable(value = "id") long userInfoId) {
        ZealInfo zealInfo = new ZealInfo();
        UserInfoVO userInfoVO = userInfoService.find(userInfoId);
        zealInfo.id = userInfoVO.getId();
        zealInfo.email = "412837184@qq.com";
        zealInfo.nickName = userInfoVO.getNickName();
        zealInfo.description = "";
        zealInfo.publishedCount = albumService.countByPublishStatus(true, userInfoId);
        zealInfo.photo = "/zeal/resources/app/icons/photo.jpg";
        return new Response.Builder().success().result(zealInfo).build();
    }


}
