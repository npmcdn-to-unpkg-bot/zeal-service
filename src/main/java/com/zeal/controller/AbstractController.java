package com.zeal.controller;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
                MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
                String contentType = mimetypesFileTypeMap.getContentType(file);
                response.setContentType(contentType);
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


    protected List<MultipartFile> resolveMultipartFiles(DefaultMultipartHttpServletRequest request) {
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
