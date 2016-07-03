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
        BufferedImage image = null;
        try {
            image = ImageIO.read(srcFile);
            Thumbnails.Builder<BufferedImage> builder = null;
            int width = image.getWidth();
            int height = image.getHeight();
            if (1.0f != (float) width / height) {
                if (width > height) {
                    image = Thumbnails.of(srcFile.getPath()).height(SIZE).asBufferedImage();
                } else {
                    image = Thumbnails.of(srcFile.getPath()).width(SIZE).asBufferedImage();
                }
                builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, SIZE, SIZE).size(SIZE, SIZE);
            } else {
                builder = Thumbnails.of(image).size(SIZE, SIZE);
            }
            builder.outputFormat("jpg").toFile(targetFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
