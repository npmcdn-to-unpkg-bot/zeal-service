package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.AlbumService;
import com.zeal.utils.SessionUtils;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 6/28/2016.
 */
@RequestMapping("/album")
@Controller
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response pagedList(@RequestParam(value = "paged", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return new Response.Builder().success().result(albumService.paged(page, pageSize)).build();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response all() {
        return new Response.Builder().success().result(albumService.findAll()).build();
    }

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response find(@PathVariable("id") long id) {
        return new Response.Builder().success().result(albumService.find(id)).build();
    }

    @RequestMapping(value = "/publish/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response publish(@PathVariable("id") long id, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        albumService.publish(id, userInfo.getId());
        return new Response.Builder().success().build();
    }

    @RequestMapping(value = "/unpublish/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response unPublish(@PathVariable("id") long id, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        albumService.unPublish(id, userInfo.getId());
        return new Response.Builder().success().build();
    }

    @RequestMapping(value = "/published", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response published(@RequestParam(value = "paged", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return new Response.Builder().success().result(albumService.published(page, pageSize)).build();
    }

    @RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response delete(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        albumService.delete(id, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
    }


}
