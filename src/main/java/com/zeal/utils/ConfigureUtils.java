package com.zeal.utils;

/**
 * Created by yang_shoulai on 2016/7/2.
 */
public class ConfigureUtils {

    private static String KEY_ALBUMS_SERVER = "zeal.albums.server";

    private static String KEY_ALBUMS_REPOSITORY = "zeal.albums.repository";



    public static String getAlbumServer(){
        return "http://localhost:8080/zeal/picture/";
    }

    public static String getAlbumRepository(){
        return "D:\\zeal-albums\\";
    }
}
