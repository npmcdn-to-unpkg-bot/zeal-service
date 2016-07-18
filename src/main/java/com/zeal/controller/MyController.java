package com.zeal.controller;

import com.zeal.common.PagedList;
import com.zeal.http.response.Response;
import com.zeal.http.response.album.AlbumAuthorInfo;
import com.zeal.http.response.album.AlbumInfo;
import com.zeal.service.AlbumCollectionService;
import com.zeal.service.AlbumService;
import com.zeal.service.UserInfoService;
import com.zeal.utils.SessionUtils;
import com.zeal.utils.StringUtils;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 6/29/2016.
 */
@RequestMapping("/my")
@Controller
public class MyController extends AbstractController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumCollectionService albumCollectionService;

    @Autowired
    private UserInfoService userInfoService;


    @RequestMapping(value = "/albums", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response albums(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                           @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                           @RequestParam(value = "state", required = false, defaultValue = "-1") int state,
                           HttpServletRequest request) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(keyword)) {
            keyword = "";
        } else {
            keyword = URLDecoder.decode(keyword, "UTF-8");
        }
        PagedList<AlbumInfo> pagedList = albumService.pagedByUserInfoIdAndKeywordLike(page, pageSize, SessionUtils.getUserInfoId(request), keyword, SessionUtils.getUserInfoId(request), state);
        return new Response.Builder().success().result(pagedList).build();
    }


    /**
     * 获取我的相关信息
     * 收藏的相册数量
     * 关注的作者数量
     * 获得的赞的数量
     * 发布的相册数量
     * 未发布的相册数量
     * 全部的相册数量
     * 我的用户信息等
     *
     * @return
     */
    @RequestMapping(value = "/zealInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response myZealInfo(HttpServletRequest request) {
        //TODO 获取我的评论数目
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        AlbumAuthorInfo myAlbumAuthorInfo = userInfoService.author(userInfo.getId(), userInfo.getId());
        return new Response.Builder().success().result(myAlbumAuthorInfo).build();
    }


}
