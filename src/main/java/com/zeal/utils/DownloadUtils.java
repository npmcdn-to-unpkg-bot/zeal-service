package com.zeal.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yang_shoulai on 2016/7/3.
 */
public class DownloadUtils {

    public static File downloadFile(String urlAddress, String filePath) {
        InputStream is = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int total = connection.getContentLength();
            is = connection.getInputStream();
            file = FileUtils.createFile(filePath);
            fos = new FileOutputStream(file);
            int count = 0;
            int progress = 0;
            byte[] buf = new byte[1024];
            int downCount = 0;
            do {
                int numRead = is.read(buf);
                count += numRead;
                progress = (int) (((float) count / total) * 100);
                if (downCount == 0 || ((progress - downCount) == 1)) {
                    downCount = progress;
                }
                if (numRead <= 0) {
                    break;
                } else {
                    fos.write(buf, 0, numRead);
                }
            } while (true);
        } catch (Exception e) {
            LogUtils.error(DownloadUtils.class, "error in download file " + filePath, e);
            if (file != null) {
                FileUtils.deleteFile(file.getPath());
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {
                LogUtils.error(DownloadUtils.class, "", ex);
            }
        }

        return file;
    }
}
