package com.zeal.worker.albums.taohua;

import com.zeal.utils.StringUtils;
import com.zeal.worker.albums.AbstractPicturesPageResolver;
import com.zeal.worker.albums.PageAlbum;
import com.zeal.worker.albums.PagePicture;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/10.
 */
public class TaoHuaPicturesPageResolver extends AbstractPicturesPageResolver {

    private static final String ROOT_URL = "http://thzhd.me";

    public TaoHuaPicturesPageResolver(PageAlbum pageAlbum) {
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
        Elements elements = document.select(".t_fsz table img");
        if (elements != null && !elements.isEmpty()) {
            for (Element image : elements) {
                String src = image.attr("file");
                if (!StringUtils.isEmpty(src)) {
                    PagePicture pagePicture = new PagePicture();
                    pagePicture.src = src;
                    pictures.add(pagePicture);
                }
            }
        }
        return pictures;
    }
}
