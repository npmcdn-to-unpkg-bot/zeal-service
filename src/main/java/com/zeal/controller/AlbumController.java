package com.zeal.controller;

import com.zeal.common.PagedList;
import com.zeal.http.response.Response;
import com.zeal.http.response.album.*;
import com.zeal.service.AlbumService;
import com.zeal.service.AuthorityCheckService;
import com.zeal.service.UserInfoService;
import com.zeal.utils.SessionUtils;
import com.zeal.utils.StringUtils;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 6/28/2016.
 */
@RequestMapping("/album")
@Controller
public class AlbumController extends AbstractController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AuthorityCheckService authorityCheckService;

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response find(@PathVariable("id") long id, HttpServletRequest request) {
        long currentUserId = SessionUtils.getUserInfoId(request);
        authorityCheckService.checkAlbumReadAuthority(request, id);
        AlbumInfo albumInfo = albumService.findDetails(id, currentUserId);
        return new Response.Builder().success().result(albumInfo).build();
    }

    @RequestMapping(value = "/publish/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response publish(@PathVariable("id") long id, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        authorityCheckService.checkAlbumModifyAuthority(request, id);
        albumService.publish(id, userInfo.getId());
        return new Response.Builder().success().result(albumService.findBasic(id)).build();
    }

    @RequestMapping(value = "/unpublish/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response unPublish(@PathVariable("id") long id, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        authorityCheckService.checkAlbumModifyAuthority(request, id);
        albumService.unPublish(id, userInfo.getId());
        return new Response.Builder().success().result(albumService.findBasic(id)).build();
    }

    @RequestMapping(value = "/published", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response published(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                              @RequestParam(value = "tag", required = false, defaultValue = "-1") long tagId, HttpServletRequest request) {
        PagedList<PagedAlbumInfo> pagedList = albumService.published(page, pageSize, tagId, SessionUtils.getUserInfoId(request));
        return new Response.Builder().success().result(pagedList).build();
    }

    @RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response delete(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        authorityCheckService.checkAlbumModifyAuthority(httpServletRequest, id);
        albumService.delete(id, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
    }

    @RequestMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response create(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "description", required = false) String description,
                           DefaultMultipartHttpServletRequest httpServletRequest) {
        List<MultipartFile> files = resolveMultipartFiles(httpServletRequest);
        albumService.create(name, description, files, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
    }

    @RequestMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response update(@RequestParam(value = "name") String name,
                           @RequestParam(value = "description", required = false) String description,
                           @RequestParam(value = "deletes", required = false) String deleteIdArray,
                           @RequestParam(value = "tags", required = false) String tagIdArray,
                           @RequestParam(value = "id") long id,
                           DefaultMultipartHttpServletRequest httpServletRequest) {
        authorityCheckService.checkAlbumModifyAuthority(httpServletRequest, id);
        List<MultipartFile> newFiles = resolveMultipartFiles(httpServletRequest);
        int[] deletes = StringUtils.splitToIntArray(deleteIdArray, ",");
        int[] tags = StringUtils.splitToIntArray(tagIdArray, ",");
        albumService.update(id, name, description, deletes, newFiles, tags, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().result(albumService.findDetails(id, SessionUtils.getUserInfoId(httpServletRequest))).build();
    }

    /**
     * 相册封面图片
     *
     * @param id
     * @param response
     */
    @RequestMapping(value = "/thumbnail/{id}")
    public void thumbnail(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) {
        authorityCheckService.checkAlbumReadAuthority(request, id);
        File file = albumService.getThumbnail(id);
        if (file == null || !file.exists()) {
            file = albumService.createThumbnail(id);
        }
        returnFile(response, file);
    }

}
