package com.zeal.worker.albums;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/2.
 */
public interface AlbumsPageResolver {

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return {@code true} 如果有下一页, {@code false} 没有下一页
     */
    boolean hasNextPage(Document currentDocument, String currentUrl);

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return 下一页相册列表页面的URL地址，{@code null}如果没有下一页
     */
    String nextPage(Document currentDocument, String currentUrl);

    /**
     * @param currentDocument 当前页面的文档对象
     * @param currentUrl      当前页面的URL路径
     * @return 获取当前页面的所有相册的详细页面路径、相册名称
     */
    List<PageAlbum> resolve(Document currentDocument, String currentUrl);


    /**
     * 获取相册详细页面的解析器
     *
     * @return
     */
    PicturesPageResolver newPicturePageResolver(PageAlbum pageAlbum);


    String getEntrance();

}
