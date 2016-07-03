package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.AlbumService;
import com.zeal.utils.SessionUtils;
import com.zeal.utils.StringUtils;
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
        return new Response.Builder().success().result(albumService.pagedByUserInfoIdAndKeywordLike(page, pageSize, SessionUtils.getUserInfo(request).getId(), keyword)).build();
    }

}
