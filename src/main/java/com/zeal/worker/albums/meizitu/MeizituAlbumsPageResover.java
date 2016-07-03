package com.zeal.worker.albums.meizitu;

import com.zeal.worker.albums.AbstractAlbumsPageResolver;
import com.zeal.worker.albums.PageAlbum;
import com.zeal.worker.albums.PicturesPageResolver;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/3.
 */
public class MeizituAlbumsPageResover extends AbstractAlbumsPageResolver {


    private static final String ROOT_URL = "http://www.meizitu.com/a/";

    private String subUrl;

    public MeizituAlbumsPageResover(String subUrl) {
        this.subUrl = subUrl;
    }

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return {@code true} 如果有下一页, {@code false} 没有下一页
     */
    @Override
    public boolean hasNextPage(Document currentDocument, String currentUrl) {
        if (currentDocument == null) return false;
        Elements navigationElement = currentDocument.select(".navigation");
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

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return 下一页相册列表页面的URL地址，{@code null}如果没有下一页
     */
    @Override
    public String nextPage(Document currentDocument, String currentUrl) {
        if (currentDocument == null) return null;
        Elements navigationElement = currentDocument.select(".navigation");
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

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return 获取当前页面的所有相册的详细页面路径、相册名称
     */
    @Override
    public List<PageAlbum> resolve(Document currentDocument, String currentUrl) {
        List<PageAlbum> albumList = new ArrayList<>();
        Element main = currentDocument.getElementById("maincontent");
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
                            PageAlbum album = new PageAlbum();
                            if (name != null) {
                                if (name.startsWith("<b>")) {
                                    name = name.substring(3);
                                }
                                if (name.endsWith("</b>")) {
                                    name = name.substring(0, name.length() - 4);
                                }
                                album.name = name;
                                album.picturesPage = key;
                                albumList.add(album);
                            }
                        }
                    }
                }
            }
        }
        return albumList;
    }

    @Override
    public PicturesPageResolver newPicturePageResolver(PageAlbum pageAlbum) {
        return new MeizituPicturesPageResolver(pageAlbum);
    }

    /**
     * 获取相册详细页面的解析器
     *
     * @return
     */


    @Override
    public String getEntrance() {
        return ROOT_URL + subUrl;
    }


}
