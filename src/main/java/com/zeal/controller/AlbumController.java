package com.zeal.controller;

import com.zeal.dao.PictureDao;
import com.zeal.entity.Picture;
import com.zeal.http.response.Response;
import com.zeal.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 6/28/2016.
 */
@RequestMapping("/album")
@Controller
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PictureDao pictureDao;

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response pagedList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Picture picture = pictureDao.find(1L);
        picture.getAlbum();
        return new Response.Builder().success().result(albumService.page(page, pageSize)).build();
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
}
