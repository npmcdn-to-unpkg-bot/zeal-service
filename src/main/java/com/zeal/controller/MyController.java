package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.AlbumService;
import com.zeal.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 6/29/2016.
 */
@RequestMapping("/my")
@Controller
public class MyController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping(value = "/albums", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response albums(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                           HttpServletRequest request) {
        return new Response.Builder().success().result(albumService.pagedByUserInfoId(page, pageSize, SessionUtils.getUserInfo(request).getId())).build();
    }

}
