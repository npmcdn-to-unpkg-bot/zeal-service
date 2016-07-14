package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.AlbumCollectionService;
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
 * Created by Administrator on 7/14/2016.
 */
@Controller
@RequestMapping("/collection/album")
public class AlbumCollectionController {


    @Autowired
    private AlbumCollectionService albumCollectionService;

    @Autowired
    private AuthorityCheckService authorityCheckService;

    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response add(@RequestParam("albumId") long albumId, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        authorityCheckService.checkAlbumReadAuthority(request, albumId);
        albumCollectionService.create(userInfo.getId(), albumId);
        return new Response.Builder().success().build();
    }


    @RequestMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response cancel(@RequestParam("albumId") long albumId, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        authorityCheckService.checkAlbumReadAuthority(request, albumId);
        albumCollectionService.delete(userInfo.getId(), albumId);
        return new Response.Builder().success().build();
    }


    /**
     * 获取相册的被收藏的数量
     *
     * @param albumId
     * @param request
     * @return
     */
    @RequestMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response count(@RequestParam("albumId") long albumId, HttpServletRequest request) {
        authorityCheckService.checkAlbumReadAuthority(request, albumId);
        long count = albumCollectionService.countByAlbumIdEquals(albumId);
        return new Response.Builder().success().result(count).build();
    }


}
