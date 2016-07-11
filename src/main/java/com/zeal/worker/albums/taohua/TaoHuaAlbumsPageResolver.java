package com.zeal.worker.albums.taohua;

import com.zeal.utils.StringUtils;
import com.zeal.worker.albums.AbstractAlbumsPageResolver;
import com.zeal.worker.albums.PageAlbum;
import com.zeal.worker.albums.PicturesPageResolver;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Created by yang_shoulai on 2016/7/10.
 */
public class TaoHuaAlbumsPageResolver extends AbstractAlbumsPageResolver {

    private static final String ROOT_URL = "http://thzhd.me/";

    private static Map<String, Set<Long>> tagMap = new HashMap<>();

    static {
        Set<Long> set1 = new HashSet<>();
        set1.add(4L);
        set1.add(7L);
        set1.add(13L);
        set1.add(12L);
        tagMap.put("forum-56-1.html", set1);
    }

    private String subUrl;

    public TaoHuaAlbumsPageResolver(String subUrl) {
        this.subUrl = subUrl;
    }

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return {@code true} 如果有下一页, {@code false} 没有下一页
     */
    @Override
    public boolean hasNextPage(Document currentDocument, String currentUrl) {
        Elements elements = currentDocument.select("#fd_page_bottom .pg a:contains(下一页)");
        return elements != null && !elements.isEmpty();
    }

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return 下一页相册列表页面的URL地址，{@code null}如果没有下一页
     */
    @Override
    public String nextPage(Document currentDocument, String currentUrl) {
        Elements elements = currentDocument.select("#fd_page_bottom .pg a:contains(下一页)");

        return ROOT_URL + elements.get(0).attr("href");
    }

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return 获取当前页面的所有相册的详细页面路径、相册名称
     */
    @Override
    public List<PageAlbum> resolve(Document currentDocument, String currentUrl) {
        List<PageAlbum> pageAlbumList = new ArrayList<>();
        Elements images = currentDocument.select("#waterfall li img");
        if (images != null && !images.isEmpty()) {
            for (Element image : images) {
                Element parent = image.parent();
                if (parent != null) {
                    String name = parent.attr("title");
                    String href = parent.attr("href");
                    if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(href)) {
                        PageAlbum pageAlbum = new PageAlbum();
                        pageAlbum.name = name;
                        pageAlbum.picturesPage = ROOT_URL + href;
                        pageAlbum.albumTags = tagMap.get(this.subUrl);
                        pageAlbumList.add(pageAlbum);
                    }
                }
            }
        }
        return pageAlbumList;
    }

    /**
     * 获取相册详细页面的解析器
     *
     * @param pageAlbum
     * @return
     */
    @Override
    public PicturesPageResolver newPicturePageResolver(PageAlbum pageAlbum) {
        return new TaoHuaPicturesPageResolver(pageAlbum);
    }

    @Override
    public String getEntrance() {
        return ROOT_URL + this.subUrl;
    }
}
