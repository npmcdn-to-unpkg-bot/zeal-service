package com.zeal.controller;

import com.zeal.common.PagedList;
import com.zeal.http.response.Response;
import com.zeal.http.response.album.AlbumQueryResult;
import com.zeal.http.response.my.ZealInfo;
import com.zeal.service.AlbumCollectionService;
import com.zeal.service.AlbumService;
import com.zeal.service.AuthorityCheckService;
import com.zeal.utils.SessionUtils;
import com.zeal.utils.StringUtils;
import com.zeal.vo.album.AlbumVO;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
    private AuthorityCheckService authorityCheckService;


    @RequestMapping(value = "/albums", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response albums(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                           @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                           HttpServletRequest request) {
        if (StringUtils.isEmpty(keyword)) {
            keyword = "";
        } else {
            try {
                keyword = URLDecoder.decode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        PagedList<AlbumVO> pagedList = albumService.pagedByUserInfoIdAndKeywordLike(page, pageSize, userInfo.getId(), keyword);
        PagedList<AlbumQueryResult> list = new PagedList<>();
        list.setSize(pagedList.getSize());
        list.setPage(pagedList.getPage());
        list.setTotalSize(pagedList.getTotalSize());
        List<AlbumQueryResult> results = new ArrayList<>();
        for (AlbumVO albumVO : pagedList.getList()) {
            AlbumQueryResult result = new AlbumQueryResult();
            BeanUtils.copyProperties(albumVO, result);
            result.setCollectionCount(albumCollectionService.countByAlbumIdEquals(albumVO.getId()));
            result.setCollected(albumCollectionService.collected(userInfo.getId(), albumVO.getId()));
            results.add(result);
        }
        list.setList(results);
        return new Response.Builder().success().result(list).build();
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
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        ZealInfo myZealInfo = new ZealInfo();
        myZealInfo.id = userInfo.getId();
        myZealInfo.nickName = userInfo.getNickName();
        myZealInfo.email = "412837184@qq.com";
        myZealInfo.photo = "/zeal/resources/app/icons/photo.jpg";
        myZealInfo.description = "";
        myZealInfo.publishedCount = albumService.countByPublishStatus(true, userInfo.getId());
        myZealInfo.unpublishedCount = albumService.countByPublishStatus(false, userInfo.getId());
        myZealInfo.collectionCount = albumCollectionService.countByUserInfoIdEquals(userInfo.getId());
        return new Response.Builder().success().result(myZealInfo).build();
    }


}
