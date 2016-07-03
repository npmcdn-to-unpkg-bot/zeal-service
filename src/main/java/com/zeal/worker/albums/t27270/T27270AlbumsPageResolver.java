package com.zeal.worker.albums.t27270;

import com.zeal.entity.Album;
import com.zeal.utils.StringUtils;
import com.zeal.worker.albums.AbstractAlbumsPageResolver;
import com.zeal.worker.albums.PageAlbum;
import com.zeal.worker.albums.PicturesPageResolver;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yang_shoulai on 2016/7/3.
 */
public class T27270AlbumsPageResolver extends AbstractAlbumsPageResolver {


    private static final String ROOT_URL = "http://www.27270.com/";

    private String subUrl;

    public T27270AlbumsPageResolver(String subUrl) {
        this.subUrl = subUrl;
    }


    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return {@code true} 如果有下一页, {@code false} 没有下一页
     */
    @Override
    public boolean hasNextPage(Document currentDocument, String currentUrl) {
        Elements elements = currentDocument.select(".NewPages li a:contains(下一页)");
        return elements != null && !elements.isEmpty();
    }

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return 下一页相册列表页面的URL地址，{@code null}如果没有下一页
     */
    @Override
    public String nextPage(Document currentDocument, String currentUrl) {
        Elements elements = currentDocument.select(".NewPages li a:contains(下一页)");
        return ROOT_URL + elements.get(0).attr("href");
    }

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return 获取当前页面的所有相册的详细页面路径、相册名称
     */
    @Override
    public List<PageAlbum> resolve(Document currentDocument, String currentUrl) {
        List<PageAlbum> albumList = new ArrayList<>();
        Map<String, Album> map = new HashMap<>();
        Elements elements = currentDocument.select(".MeinvTuPianBox ul li");
        if (elements != null && !elements.isEmpty()) {
            for (Element element : elements) {
                Element a = element.select("a").first();
                String key = a.attr("href");
                String name = a.attr("title");
                if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(name)) {
                    PageAlbum album = new PageAlbum();
                    album.picturesPage = key;
                    album.name = name;
                    albumList.add(album);
                }
            }

        }
        return albumList;
    }

    /**
     * 获取相册详细页面的解析器
     *
     * @param pageAlbum
     * @return
     */
    @Override
    public PicturesPageResolver newPicturePageResolver(PageAlbum pageAlbum) {
        return new T27270PicturesPageResolver(pageAlbum);
    }

    @Override
    public String getEntrance() {
        return ROOT_URL + subUrl;
    }
}
