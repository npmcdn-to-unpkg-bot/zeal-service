package com.zeal.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 6/29/2016.
 */
public class FileUtils {


    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile() && !file.delete()) {
                throw new RuntimeException("can not delete file path = " + path);
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    deleteFile(f.getPath());
                }
                file.delete();
            }
        }
    }

    public static File createFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
