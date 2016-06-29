package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.PictureService;
import com.zeal.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 6/29/2016.
 */

@RequestMapping("/picture")
@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/{id}")
    @ResponseBody
    public Response find(@PathVariable("id") long id) {
        return new Response.Builder().success().result(pictureService.find(id)).build();
    }


    @RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response delete(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        pictureService.delete(id, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
    }

}
