package com.zeal.controller;

import com.zeal.http.response.Response;
import com.zeal.service.PictureService;
import com.zeal.utils.ConfigureUtils;
import com.zeal.utils.SessionUtils;
import com.zeal.vo.album.PictureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 6/29/2016.
 */

@RequestMapping("/picture")
@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping(value = "/{id}")
    public void find(@PathVariable("id") long id, HttpServletResponse response) {
        File file = pictureService.getDiskFile(id);
        if (file.exists() && file.canRead()) {
            FileInputStream fileInputStream = null;
            OutputStream stream = null;
            try {
                fileInputStream = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fileInputStream.read(data);
                response.setContentType("image/jpeg");
                stream = response.getOutputStream();
                stream.write(data);
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


    @RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response delete(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        pictureService.delete(id, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
    }

}
