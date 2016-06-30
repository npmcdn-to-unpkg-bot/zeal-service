package com.zeal.worker.fengjing;

import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import com.zeal.utils.StringUtils;
import com.zeal.worker.AlbumWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yang_shoulai on 2016/6/30.
 */
public class T27270AlbumWorker implements AlbumWorker {

    private static final String ROOT_URL = "http://www.27270.com/";

    private String subUrl;

    public T27270AlbumWorker(String subUrl) {
        this.subUrl = subUrl;
    }

    @Override
    public String firstPageUrl() {
        return ROOT_URL + subUrl;
    }

    @Override
    public boolean hasNext(Document document) {
        Elements elements = document.select(".NewPages li a:contains(下一页)");

        return elements != null && !elements.isEmpty();
    }

    @Override
    public String nextUrl(Document document, String currentUrl) {
        Elements elements = document.select(".NewPages li a:contains(下一页)");
        return ROOT_URL + elements.get(0).attr("href");
    }

    @Override
    public Map<String, Album> resoveAlbums(Document document, String currentUrl) {
        Map<String, Album> map = new HashMap<>();
        Elements elements = document.select(".MeinvTuPianBox ul li");
        if (elements != null && !elements.isEmpty()) {
            for (Element element : elements) {
                Element a = element.select("a").first();
                String key = a.attr("href");
                String name = a.attr("title");
                if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(name)) {
                    Album album = new Album();
                    album.setName(name);
                    map.put(key, album);
                }
            }

        }
        return map;
    }

    @Override
    public List<Picture> resovePictures(Document document, String currentUrl) {
        List<Picture> pictures = new ArrayList<>();
        while (true) {
            Elements imgs = document.select("#mouse img");
            if (imgs != null && !imgs.isEmpty()) {
                String src = imgs.get(0).attr("src");
                if (!StringUtils.isEmpty(src)) {
                    Picture picture = new Picture();
                    picture.setUrl(src);
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
