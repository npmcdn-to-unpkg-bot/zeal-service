package com.zeal.worker.albums.meizitu;

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
public class MeizituPicturesPageResolver extends AbstractPicturesPageResolver {


    public MeizituPicturesPageResolver(PageAlbum pageAlbum) {
        super(pageAlbum);
    }

    /**
     * 从相册的详情页面解析所有的相册图片
     *
     * @return
     */
    @Override
    public List<PagePicture> resolve(Document document, String currentUrl) {
        List<PagePicture> pictures = new ArrayList<>();
        Elements elements = document.select("#picture img");
        if (elements == null || elements.isEmpty()) {
            elements = document.select(".postContent img");
        }
        if (elements != null && !elements.isEmpty()) {
            for (Element img : elements) {
                String src = img.attr("src");
                PagePicture picture = new PagePicture();
                picture.src = src;
                pictures.add(picture);
            }
        }
        return pictures;
    }



}
