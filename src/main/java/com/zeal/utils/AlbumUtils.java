package com.zeal.utils;

import com.zeal.entity.Album;
import com.zeal.entity.Picture;

import java.io.File;

/**
 * Created by yang_shoulai on 2016/7/2.
 */
public class AlbumUtils {

    /**
     * 删除硬盘中图片的文件
     *
     * @param picture
     */
    public static void deleteDiskFile(Picture picture) {
        File file = getDiskFile(picture);
        FileUtils.deleteFile(file.getPath());
    }

    /**
     * 获取硬盘中图片的文件
     *
     * @param picture
     * @return
     */
    public static File getDiskFile(Picture picture) {
        return new File(ConfigureUtils.getAlbumRepository() + picture.getUrl());
    }


    /**
     * 获取硬盘中相册所在的文件夹
     * @param album
     * @return
     */
    public static File getAlbumFolder(Album album) {
        return new File(ConfigureUtils.getAlbumRepository() + album.getUserInfo().getId() + File.separator + album.getId());
    }

}
