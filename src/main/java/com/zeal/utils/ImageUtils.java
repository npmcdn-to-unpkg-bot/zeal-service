package com.zeal.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            builder.outputFormat("jpg").toFile(targetFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
