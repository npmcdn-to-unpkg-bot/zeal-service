package com.zeal.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * Created by yang_shoulai on 2016/7/3.
 */
public class ImageUtils {

    private static final int SIZE = 240;

    public static void createThumbnail(File srcFile, File targetFile) {
        resize(srcFile, targetFile, SIZE, SIZE);
    }

    public static void resize(File srcFile, File targetFile, int targetWidth, int targetHeight) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(srcFile);
            Thumbnails.Builder<BufferedImage> builder = null;
            int width = image.getWidth();
            int height = image.getHeight();
            if (1.0f != (float) width / height) {
                if (width > height) {
                    image = Thumbnails.of(srcFile.getPath()).height(targetHeight).asBufferedImage();
                } else {
                    image = Thumbnails.of(srcFile.getPath()).width(targetWidth).asBufferedImage();
                }
                builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, targetWidth, targetHeight).size(targetWidth, targetHeight);
            } else {
                builder = Thumbnails.of(image).size(targetWidth, targetHeight);
            }
            String fileType = "jpeg";
            if (targetFile.getName().lastIndexOf(".") != -1) {
                String type = targetFile.getName().substring(targetFile.getName().lastIndexOf("."));
                if (".gif".equalsIgnoreCase(type)) {
                    fileType = "gif";
                }
            }
            builder.outputFormat(fileType).toFile(targetFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将base64字符串转换成图片并保存
     *
     * @param imgStr
     * @param savePath
     * @throws IOException
     */
    public static void base64ToImage(String imgStr, String savePath) throws IOException {
        if (imgStr == null) throw new IllegalArgumentException();
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        File file;
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            file = FileUtils.createFile(savePath);
            out = new FileOutputStream(file);
            out.write(b);
            out.flush();
        } catch (IOException e) {
            FileUtils.deleteFile(savePath);
            throw e;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取图片的类型
     *
     * @param imageBytes
     * @return
     * @throws IOException
     */
    public static String getImageType(final byte[] imageBytes) throws IOException {
        if (!isImage(imageBytes)) {
            throw new IllegalArgumentException("not an image");
        }
        ImageInputStream imageInput = null;
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(imageBytes);
            imageInput = ImageIO.createImageInputStream(input);
            Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInput);
            String type = null;
            if (iterator.hasNext()) {
                ImageReader reader = iterator.next();
                type = reader.getFormatName().toLowerCase();
            }
            return type;
        } finally {
            try {
                if (imageInput != null)
                    imageInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取base64编码后图片的类型
     *
     * @param base64Str
     * @return
     */
    public static String getImageType(String base64Str) throws IOException {
        if (base64Str == null) throw new IllegalArgumentException();
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(base64Str);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {//调整异常数据
                b[i] += 256;
            }
        }
        return getImageType(b);
    }

    /**
     * 判断字符串是否能转为图片
     *
     * @param bytes
     * @return
     */
    public static boolean isImage(byte[] bytes) throws IOException {
        if (bytes == null) throw new IllegalArgumentException();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(inputStream);
        return image != null;
    }

    /**
     * 判断字符串是否为base64编码的图片
     * @param base64Str
     * @return
     * @throws IOException
     */
    public static boolean isImage(String base64Str) throws IOException {
        if (base64Str == null) throw new IllegalArgumentException();
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(base64Str);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {//调整异常数据
                b[i] += 256;
            }
        }
        return isImage(b);
    }
}
