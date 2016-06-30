package com.zeal.worker;

import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by yang_shoulai on 2016/6/29.
 */
public interface AlbumWorker {

    /**
     * 获取第一页的url
     *
     * @return
     */
    String firstPageUrl();

    /**
     * 是否包含下一页
     *
     * @param document
     * @return
     */
    boolean hasNext(Document document);

    /**
     * 获取下一页网页的连接
     *
     * @param document
     * @return
     */
    String nextUrl(Document document, String currentUrl);

    /**
     * 获取当前文档中的相册信息
     *
     * @param document
     * @return 相册信息map, key为相册的网页连接，value为相册基本信息
     */
    Map<String, Album> resoveAlbums(Document document, String currentUrl);

    /**
     * 获取当前文档中的图片信息
     *
     * @param document
     * @return
     */
    List<Picture> resovePictures(Document document, String currentUrl);


}
