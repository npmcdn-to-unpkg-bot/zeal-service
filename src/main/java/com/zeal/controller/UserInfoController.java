package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/userinfo")
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Response post(@PathVariable Long id) {
        return new Response.Builder().success().result(userInfoService.find(id)).build();
    }


}
