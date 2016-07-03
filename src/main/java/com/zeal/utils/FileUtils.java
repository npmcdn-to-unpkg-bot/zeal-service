package com.zeal.utils;

import org.aspectj.util.FileUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

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


    public static void copyFile(File srcFile, File targetFile) {
        if (!srcFile.exists()) {
            throw new IllegalArgumentException("Src file not exist!");
        }
        if (targetFile.exists()) {
            deleteFile(targetFile.getPath());
        }
        targetFile = createFile(targetFile.getPath());
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(srcFile);
            outputStream = new FileOutputStream(targetFile);
            copyStream(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[4096];
        for (int bytesRead = in.read(buf, 0, 4096); bytesRead != -1; bytesRead = in.read(buf, 0, 4096)) {
            out.write(buf, 0, bytesRead);
        }

    }
}
