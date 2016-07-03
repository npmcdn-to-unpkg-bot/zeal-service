package com.zeal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yang_shoulai on 2016/7/2.
 */
public class ConfigureUtils {

    private static String KEY_ALBUMS_SERVER = "zeal.albums.server";

    private static String KEY_ALBUMS_REPOSITORY = "zeal.albums.repository";


    private static Properties properties;

    static {
        properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = ConfigureUtils.class.getResourceAsStream("/META-INF/spring/configure.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            LogUtils.error(ConfigureUtils.class, "Exception when load configure properties!", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getAlbumServer() {
        return properties.getProperty(KEY_ALBUMS_SERVER);
    }

    public static String getAlbumRepository() {
        return properties.getProperty(KEY_ALBUMS_REPOSITORY);
    }

    public static String getString(String key) {
        return properties.getProperty(key, null);
    }

    public static String getString(String key, String def) {
        return properties.getProperty(key, def);
    }

    /**
     * return int properties value
     *
     * @param key properties key
     * @return
     * @throws NumberFormatException if the property can not be parsed to int
     * @throws NullPointerException  if the property is not exist
     */
    public static int getInt(String key) throws NumberFormatException, NullPointerException {
        String p = getString(key);
        if (p == null) {
            throw new NullPointerException("can not find properties by key " + key);
        }
        return Integer.valueOf(p);
    }


    public static int getInt(String key, int def) throws NumberFormatException {
        String p = getString(key);
        if (p == null) {
            return def;
        }
        return Integer.valueOf(p);
    }
}
