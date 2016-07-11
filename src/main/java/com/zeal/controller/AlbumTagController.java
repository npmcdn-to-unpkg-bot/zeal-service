package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.AlbumTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yang_shoulai on 2016/7/8.
 */
@RequestMapping("/albumTag")
@Controller
public class AlbumTagController {

    @Autowired
    private AlbumTagService albumTagService;

    @RequestMapping("/children")
    @ResponseBody
    public Response findChildren(@RequestParam("id") long id) {
        return new Response.Builder().success().result(albumTagService.findChildrenByTagId(id)).build();
    }


    @RequestMapping("/all")
    @ResponseBody
    public Response findChildren() {
        return new Response.Builder().success().result(albumTagService.findAllChildren()).build();
    }

}
