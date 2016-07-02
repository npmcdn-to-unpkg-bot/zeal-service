package com.zeal.utils;

import com.zeal.entity.Picture;

import java.io.File;

/**
 * Created by yang_shoulai on 2016/7/2.
 */
public class PictureUtils {

    public static void deleteDiskFile(Picture picture) {
        File file = getDiskFile(picture);
        FileUtils.deleteFile(file.getPath());
    }

    public static File getDiskFile(Picture picture) {
        return new File(ConfigureUtils.getAlbumRepository() + picture.getUrl());
    }
}
