package com.zeal.worker;

import com.zeal.dao.AlbumDao;
import com.zeal.dao.PictureDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import com.zeal.entity.UserInfo;
import com.zeal.utils.LogUtils;
import com.zeal.utils.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yang_shoulai on 2016/6/29.
 */

@Component
public class AlbumWorkerExecutor {

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private UserInfoDao userInfoDao;


    public void execute(AlbumWorker albumWorker) {
        List<Album> albumList = new ArrayList<>();
        String url = albumWorker.firstPageUrl();
        while (true) {
            try {
                Document document = Jsoup.connect(url).timeout(10000).get();
                if (document != null) {
                    Map<String, Album> albumMap = albumWorker.resoveAlbums(document);
                    if (albumMap != null && !albumMap.isEmpty()) {
                        for (Map.Entry<String, Album> entry : albumMap.entrySet()) {
                            Document picDoc = Jsoup.connect(entry.getKey()).timeout(10000).get();
                            if (picDoc != null) {
                                List<Picture> pictures = albumWorker.resovePictures(picDoc);
                                if (pictures != null && !pictures.isEmpty()) {
                                    entry.getValue().setPictures(pictures);
                                    albumList.add(entry.getValue());
                                } else {
                                    LogUtils.error(this.getClass(), "无法解析图片列表");
                                }
                            } else {
                                LogUtils.error(this.getClass(), "无法获取图片列表页面");
                                break;
                            }
                        }
                    } else {
                        LogUtils.error(this.getClass(), "无法解析相册列表");
                    }
                    if (albumWorker.hasNext(document)) {
                        url = albumWorker.nextUrl(document);
                        if (StringUtils.isEmpty(url)) {
                            break;
                        }
                    } else {
                        break;
                    }

                } else {
                    break;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        saveAlbum(albumList);
    }

    private void saveAlbum(List<Album> albums) {
        if (albums.isEmpty()) return;
        UserInfo userInfo = userInfoDao.find(1L);
        for (Album album : albums) {
            saveAlbum(album, userInfo);
        }

    }


    @Transactional
    private void saveAlbum(Album album, UserInfo userInfo) {
        List<Picture> pictures = album.getPictures();
        if (pictures == null || pictures.isEmpty()) return;
        Album toSave = new Album();
        toSave.setName(album.getName());
        toSave.setCreateDate(new Date());
        toSave.setPublished(true);
        toSave.setUpdateDate(new Date());
        toSave.setUserInfo(userInfo);

        albumDao.insert(toSave);
        for (Picture picture : pictures) {
            picture.setAlbum(toSave);
        }
        pictureDao.batchInsert(pictures);
    }

}
