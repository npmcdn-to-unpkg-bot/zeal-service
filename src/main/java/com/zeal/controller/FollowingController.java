package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.FollowingService;
import com.zeal.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yang_shoulai on 2016/7/21.
 */
@Controller
@RequestMapping("/follow")
public class FollowingController {

    @Autowired
    private FollowingService followingService;

    @RequestMapping("/add")
    @ResponseBody
    public Response follow(@RequestParam("followed") long followed, HttpServletRequest request) {

        followingService.create(SessionUtils.getUserInfoId(request), followed);

        return new Response.Builder().success().build();
    }

    @RequestMapping("/cancel")
    @ResponseBody
    public Response cancelFollow(@RequestParam("followed") long followed, HttpServletRequest request) {
        followingService.delete(SessionUtils.getUserInfoId(request), followed);
        return new Response.Builder().success().build();
    }

}
