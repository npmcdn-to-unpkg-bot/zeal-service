package com.zeal.worker.meizitu;

import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import com.zeal.worker.AlbumWorker;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yang_shoulai on 2016/6/29.
 */
public class MeizituAlbumWorker implements AlbumWorker {

    private static final String ROOT_URL = "http://www.meizitu.com/a/";

    private String subUrl;

    public MeizituAlbumWorker(String subUrl) {
        this.subUrl = subUrl;
    }

    @Override
    public String firstPageUrl() {
        return ROOT_URL + this.subUrl;
    }

    @Override
    public boolean hasNext(Document document) {
        if (document == null) return false;
        Elements navigationElement = document.select(".navigation");
        if (navigationElement != null && navigationElement.size() > 0) {
            Elements elements = navigationElement.get(0).select("#wp_page_numbers > ul > li > a");
            if (elements != null) {
                for (Element element : elements) {
                    String html = element.html();
                    if (html != null && html.trim().equals("下一页")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String nextUrl(Document document, String currentUrl) {
        if (document == null) return null;
        Elements navigationElement = document.select(".navigation");
        if (navigationElement != null && navigationElement.size() > 0) {
            Elements elements = navigationElement.get(0).select("#wp_page_numbers > ul > li > a");
            if (elements != null) {
                for (Element element : elements) {
                    String html = element.html();
                    if (html != null && html.trim().equals("下一页")) {
                        return ROOT_URL + element.attr("href");
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, Album> resoveAlbums(Document document, String currentUrl) {
        Map<String, Album> map = new HashMap<>();
        Element main = document.getElementById("maincontent");
        if (main != null) {
            Elements lis = main.select("li");
            if (lis != null && !lis.isEmpty()) {
                for (Element li : lis) {
                    Elements as = li.select("a");
                    if (as != null && as.size() > 0) {
                        String key = as.get(0).attr("href");
                        Element img = as.get(0).select("img").first();
                        if (img != null) {
                            String name = img.attr("alt");
                            Album album = new Album();
                            if (name != null) {
                                if (name.startsWith("<b>")) {
                                    name = name.substring(3);
                                }
                                if (name.endsWith("</b>")) {
                                    name = name.substring(0, name.length() - 4);
                                }
                                album.setName(name);
                                map.put(key, album);
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    @Override
    public List<Picture> resovePictures(Document document, String currentUrl) {
        List<Picture> pictures = new ArrayList<>();
        Elements elements = document.select("#picture img");
        if (elements == null || elements.isEmpty()) {
            elements = document.select(".postContent img");
        }
        if (elements != null && !elements.isEmpty()) {
            for (Element img : elements) {
                String src = img.attr("src");
                Picture picture = new Picture();
                picture.setUrl(src);
                pictures.add(picture);
            }
        }
        return pictures;
    }
}
