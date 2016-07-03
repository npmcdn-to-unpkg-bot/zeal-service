package com.zeal.worker.albums;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/2.
 */
public interface PicturesPageResolver {

    /**
     * 从相册的详情页面解析所有的相册图片
     *
     * @return
     */
    List<PagePicture> resolve(Document document, String currentUrl);

    /**
     * 获取需要解析的相册信息
     *
     * @return
     */
    PageAlbum getPageAlbum();

}
