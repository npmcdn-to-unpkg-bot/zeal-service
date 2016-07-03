package com.zeal.worker;

import com.zeal.dao.AlbumDao;
import com.zeal.dao.PictureDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.entity.UserInfo;
import com.zeal.service.AlbumService;
import com.zeal.utils.LogUtils;
import com.zeal.worker.albums.AlbumsPageResolver;
import com.zeal.worker.albums.PageAlbum;
import com.zeal.worker.albums.PagePicture;
import com.zeal.worker.albums.PicturesPageResolver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yang_shoulai on 2016/6/29.
 */

@Component
public class AlbumWorkerExecutor {

    private static int POOL_SIZE = 10;

    private static int TIMEOUT = 5000;

    private ExecutorService workersPool = Executors.newFixedThreadPool(POOL_SIZE);

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private UserInfoDao userInfoDao;


    public void execute(AlbumsPageResolver albumsPageResolver) {
        UserInfo userInfo = userInfoDao.find(1);
        LogUtils.info(this.getClass(), "开始解析....");
        String entranceUrl = albumsPageResolver.getEntrance();
        LogUtils.info(this.getClass(), "入口地址 = " + entranceUrl);
        do {
            try {
                Document albumsDocument = Jsoup.connect(entranceUrl).timeout(TIMEOUT).get();
                List<PageAlbum> pageAlbumList = albumsPageResolver.resolve(albumsDocument, entranceUrl);
                if (pageAlbumList.isEmpty()) {
                    LogUtils.error(this.getClass(), "获取相册列表页面" + entranceUrl + "失败");
                }
                for (PageAlbum pageAlbum : pageAlbumList) {
                    PicturesPageResolver picturesPageResolver = albumsPageResolver.newPicturePageResolver(pageAlbum);
                    workersPool.execute(new Worker(picturesPageResolver, userInfo));
                }
                if (albumsPageResolver.hasNextPage(albumsDocument, entranceUrl)) {
                    entranceUrl = albumsPageResolver.nextPage(albumsDocument, entranceUrl);
                } else {
                    break;
                }
            } catch (Exception e) {
                LogUtils.error(this.getClass(), "连接相册列表地址" + entranceUrl + "失败, 退出！", e);
                break;
            }

        } while (true);
    }

    private class Worker implements Runnable {

        public PicturesPageResolver picturesPageResolver;

        public UserInfo userInfo;

        public Worker(PicturesPageResolver picturesPageResolver, UserInfo userInfo) {
            this.picturesPageResolver = picturesPageResolver;
            this.userInfo = userInfo;
        }

        @Override
        public void run() {
            PageAlbum pageAlbum = picturesPageResolver.getPageAlbum();
            Document document = null;
            List<PagePicture> pictures = null;
            try {
                document = Jsoup.connect(pageAlbum.picturesPage).timeout(5000).get();
                pictures = picturesPageResolver.resolve(document, pageAlbum.picturesPage);
                pageAlbum.pictures = pictures;
            } catch (IOException e) {
                LogUtils.error(this.getClass(), "无法获取页面" + pageAlbum.picturesPage + "的图片信息");
                return;
            }
            albumService.save(pageAlbum, userInfo);
        }
    }

}
