package com.zeal.worker.albums.t27270;

import com.zeal.entity.Picture;
import com.zeal.utils.StringUtils;
import com.zeal.worker.albums.AbstractPicturesPageResolver;
import com.zeal.worker.albums.PageAlbum;
import com.zeal.worker.albums.PagePicture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/3.
 */
public class T27270PicturesPageResolver extends AbstractPicturesPageResolver {

    public T27270PicturesPageResolver(PageAlbum pageAlbum) {
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
        while (true) {
            Elements imgs = document.select("#mouse img");
            if (imgs != null && !imgs.isEmpty()) {
                String src = imgs.get(0).attr("src");
                if (!StringUtils.isEmpty(src)) {
                    PagePicture picture = new PagePicture();
                    picture.src = src;
                    pictures.add(picture);
                }
            }
            Elements nexts = document.select(".articleV2Page li a:contains(下一页)");
            if (nexts == null || nexts.isEmpty()) {
                break;
            } else {
                String href = nexts.get(0).attr("href");
                if (!StringUtils.isEmpty(href)) {
                    String u = currentUrl.substring(0, currentUrl.lastIndexOf("/"));
                    try {
                        document = Jsoup.connect(u + "/" + href).timeout(5000).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }

        }
        return pictures;
    }
}
