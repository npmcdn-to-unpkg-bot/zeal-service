package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.AlbumAppreciationService;
import com.zeal.service.AlbumAuthorAppreciationService;
import com.zeal.service.AuthorityCheckService;
import com.zeal.utils.SessionUtils;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yang_shoulai on 2016/7/16.
 */

@Controller
@RequestMapping("/appreciation")
public class AlbumAppreciationController {

    @Autowired
    private AuthorityCheckService authorityCheckService;

    @Autowired
    private AlbumAppreciationService albumAppreciationService;

    @Autowired
    private AlbumAuthorAppreciationService albumAuthorAppreciationService;

    @RequestMapping(value = "/album/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response add(@RequestParam("albumId") long albumId, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        authorityCheckService.checkAlbumReadAuthority(request, albumId);
        albumAppreciationService.create(userInfo.getId(), albumId);
        return new Response.Builder().success().build();
    }

    @RequestMapping(value = "/album/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response cancel(@RequestParam("albumId") long albumId, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        authorityCheckService.checkAlbumReadAuthority(request, albumId);
        albumAppreciationService.delete(userInfo.getId(), albumId);
        return new Response.Builder().success().build();
    }


    @RequestMapping(value = "/author/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response addAuthorAppreciation(@RequestParam("appreciated") long appreciated, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        albumAuthorAppreciationService.create(userInfo.getId(), appreciated);
        return new Response.Builder().success().build();
    }

    @RequestMapping(value = "/author/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response cancelAuthorAppreciation(@RequestParam("appreciated") long appreciated, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        albumAuthorAppreciationService.delete(userInfo.getId(), appreciated);
        return new Response.Builder().success().build();
    }
}
