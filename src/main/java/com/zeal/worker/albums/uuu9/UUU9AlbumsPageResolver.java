package com.zeal.worker.albums.uuu9;

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
public class UUU9AlbumsPageResolver extends AbstractAlbumsPageResolver {


    private static final String ROOT_URL = "http://dota2.uuu9.com/List/";

    private String subUrl;

    public UUU9AlbumsPageResolver(String subUrl) {
        this.subUrl = subUrl;
    }

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return {@code true} 如果有下一页, {@code false} 没有下一页
     */
    @Override
    public boolean hasNextPage(Document currentDocument, String currentUrl) {
        Elements elements = currentDocument.select("div.css_page_box a:contains(下一页)");
        return elements != null && !elements.isEmpty();
    }

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return 下一页相册列表页面的URL地址，{@code null}如果没有下一页
     */
    @Override
    public String nextPage(Document currentDocument, String currentUrl) {
        Elements elements = currentDocument.select("div.css_page_box a:contains(下一页)");
        String href = elements.get(0).attr("href");
        href = href.substring(href.lastIndexOf("/") + 1, href.length());
        return ROOT_URL + href;
    }

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return 获取当前页面的所有相册的详细页面路径、相册名称
     */
    @Override
    public List<PageAlbum> resolve(Document currentDocument, String currentUrl) {
        List<PageAlbum> list = new ArrayList<>();
        Elements pics = currentDocument.select(".piclist .picbox a");
        if (pics != null && !pics.isEmpty()) {
            for (Element element : pics) {
                PageAlbum pageAlbum = new PageAlbum();
                pageAlbum.name = element.attr("title");
                pageAlbum.picturesPage = element.attr("href");
                list.add(pageAlbum);
            }
        }
        return list;
    }

    /**
     * 获取相册详细页面的解析器
     *
     * @param pageAlbum
     * @return
     */
    @Override
    public PicturesPageResolver newPicturePageResolver(PageAlbum pageAlbum) {
        return new UUU9PicturesPageResolver(pageAlbum);
    }

    @Override
    public String getEntrance() {
        return ROOT_URL + subUrl;
    }
}
