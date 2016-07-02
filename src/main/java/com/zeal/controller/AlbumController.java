package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.AlbumService;
import com.zeal.utils.SessionUtils;
import com.zeal.utils.StringUtils;
import com.zeal.vo.user.UserInfoVO;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public Response pagedList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
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
        return new Response.Builder().success().result(albumService.find(id)).build();
    }

    @RequestMapping(value = "/unpublish/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response unPublish(@PathVariable("id") long id, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        albumService.unPublish(id, userInfo.getId());
        return new Response.Builder().success().result(albumService.find(id)).build();
    }

    @RequestMapping(value = "/published", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response published(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return new Response.Builder().success().result(albumService.published(page, pageSize)).build();
    }

    @RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response delete(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
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
                           @RequestParam(value = "id") long id,
                           DefaultMultipartHttpServletRequest httpServletRequest) {
        List<MultipartFile> newFiles = resolveMultipartFiles(httpServletRequest);
        int[] deletes = null;
        if (!StringUtils.isEmpty(deleteIdArray)) {
            String[] strs = deleteIdArray.split(",");
            deletes = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                deletes[i] = Integer.valueOf(strs[i]);
            }
        }
        albumService.update(id, name, description, deletes, newFiles, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
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

}
