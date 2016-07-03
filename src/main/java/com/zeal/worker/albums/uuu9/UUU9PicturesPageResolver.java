package com.zeal.worker.albums.uuu9;

import com.zeal.worker.albums.AbstractPicturesPageResolver;
import com.zeal.worker.albums.PageAlbum;
import com.zeal.worker.albums.PagePicture;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/3.
 */
public class UUU9PicturesPageResolver extends AbstractPicturesPageResolver {


    public UUU9PicturesPageResolver(PageAlbum pageAlbum) {
        super(pageAlbum);
    }

    /**
     * 从相册的详情页面解析所有的相册图片
     *
     * @param document
     * @param currentUrl
     * @return
     */
    @Override
    public List<PagePicture> resolve(Document document, String currentUrl) {
        List<PagePicture> pictures = new ArrayList<>();
        Elements elements = document.select("#content img");
        if (elements != null) {
            for (Element element : elements) {
                PagePicture pagePicture = new PagePicture();
                pagePicture.src = element.attr("url");
                pictures.add(pagePicture);
            }
        }
        return pictures;
    }

}
