package com.zeal.controller;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by yang_shoulai on 2016/7/3.
 */
public abstract class AbstractController {

    /**
     * 返回图片
     *
     * @param response
     * @param file
     */
    protected void returnFile(HttpServletResponse response, File file) {
        if (file.exists() && file.canRead()) {
            FileInputStream fileInputStream = null;
            OutputStream stream = null;
            try {
                fileInputStream = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fileInputStream.read(data);
                response.setContentType("image/jpeg");
                String fileName = file.getName();
                if (fileName.lastIndexOf(".") != -1) {
                    String type = fileName.substring(fileName.lastIndexOf("."));
                    if (".gif".equalsIgnoreCase(type)) {
                        response.setContentType("image/gif");
                    }
                }
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
}
