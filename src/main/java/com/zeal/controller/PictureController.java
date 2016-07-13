package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.PictureService;
import com.zeal.utils.FileUtils;
import com.zeal.utils.ImageUtils;
import com.zeal.utils.SessionUtils;
import com.zeal.vo.album.AlbumVO;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by Administrator on 6/29/2016.
 */

@RequestMapping("/picture")
@Controller
public class PictureController extends AbstractController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping(value = "/{id}")
    public void find(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) {
        checkPictureAccess(id, request);
        File file = pictureService.getDiskFile(id);
        returnFile(response, file);
    }


    @RequestMapping(value = "/resize/{id}")
    public void resize(@PathVariable("id") long id, @RequestParam("width") int width, @RequestParam("height") int height, HttpServletRequest request, HttpServletResponse response) {
         checkPictureAccess(id, request);
        File file = pictureService.getDiskFile(id);
        if (width <= 0 || height <= 0) {
            returnFile(response, file);
        } else {
            File albumDir = file.getParentFile();
            String path = albumDir.getPath() + File.separator + width + "x" + height + File.separator + id + ".jpeg";
            File f = new File(path);
            if (f.exists()) {
                returnFile(response, f);
            } else {
                File targetFile = FileUtils.createFile(path);
                ImageUtils.resize(file, targetFile, width, height);
                returnFile(response, targetFile);
            }
        }
    }


    @RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response delete(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        pictureService.checkAuthority(id, SessionUtils.getUserInfo(httpServletRequest).getId());
        pictureService.delete(id, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
    }


    private void checkPictureAccess(long pictureId, HttpServletRequest request) {
        AlbumVO albumVO = pictureService.findAlbumByPictureId(pictureId);
        if (!albumVO.isPublished()) {
            UserInfoVO userInfoVO = SessionUtils.getUserInfo(request);
            long userId = 0L;
            if (userInfoVO != null) {
                userId = userInfoVO.getId();
            }
            pictureService.checkAuthority(pictureId, userId);
        }
    }
}
