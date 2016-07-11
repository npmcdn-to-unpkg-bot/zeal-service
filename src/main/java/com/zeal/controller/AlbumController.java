package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.AlbumService;
import com.zeal.utils.SessionUtils;
import com.zeal.utils.StringUtils;
import com.zeal.vo.album.AlbumVO;
import com.zeal.vo.user.UserInfoVO;
import com.zeal.worker.AlbumWorkerExecutor;
import com.zeal.worker.albums.meizitu.MeizituAlbumsPageResover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 6/28/2016.
 */
@RequestMapping("/album")
@Controller
public class AlbumController extends AbstractController {

    @Autowired
    private AlbumService albumService;


    @RequestMapping(value = "/publish/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response publish(@PathVariable("id") long id, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        albumService.checkAuthority(id, userInfo.getId());
        albumService.publish(id, userInfo.getId());
        return new Response.Builder().success().result(albumService.find(id)).build();
    }

    @RequestMapping(value = "/unpublish/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response unPublish(@PathVariable("id") long id, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        albumService.checkAuthority(id, userInfo.getId());
        albumService.unPublish(id, userInfo.getId());
        return new Response.Builder().success().result(albumService.find(id)).build();
    }

    @RequestMapping(value = "/published", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response published(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                              @RequestParam(value = "tag", required = false, defaultValue = "-1") long tagId) {
        return new Response.Builder().success().result(albumService.published(page, pageSize, tagId)).build();
    }

    @RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response delete(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        albumService.checkAuthority(id, SessionUtils.getUserInfo(httpServletRequest).getId());
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
        albumService.checkAuthority(id, SessionUtils.getUserInfo(httpServletRequest).getId());
        List<MultipartFile> newFiles = resolveMultipartFiles(httpServletRequest);
        int[] deletes = stringToArray(deleteIdArray);
        int[] tags = stringToArray(tagIdArray);
        albumService.update(id, name, description, deletes, newFiles, tags, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
    }

    /**
     * 相册封面图片
     *
     * @param id
     * @param response
     */
    @RequestMapping(value = "/thumbnail/{id}")
    public void thumbnail(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) {
        AlbumVO albumVO = albumService.find(id);
        if (!albumVO.isPublished()) {
            UserInfoVO userInfoVO = SessionUtils.getUserInfo(request);
            albumService.checkAuthority(id, userInfoVO == null ? 0L : userInfoVO.getId());
        }
        File file = albumService.getThumbnail(id);
        if (file == null || !file.exists()) {
            file = albumService.createThumbnail(id);
        }
        returnFile(response, file);
    }


    private List<MultipartFile> resolveMultipartFiles(DefaultMultipartHttpServletRequest request) {
        List<MultipartFile> files = new ArrayList<>();
        MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
        if (map != null && !map.isEmpty()) {
            Collection<List<MultipartFile>> collection = map.values();
            if (!collection.isEmpty()) {
                for (List<MultipartFile> list : collection) {
                    files.addAll(list);
                }
            }
        }
        return files;
    }


    private int[] stringToArray(String ArrayStr) {
        int[] tags = null;
        if (!StringUtils.isEmpty(ArrayStr)) {
            String[] strs = ArrayStr.split(",");
            tags = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                tags[i] = Integer.valueOf(strs[i]);
            }
        }
        return tags;
    }

}
