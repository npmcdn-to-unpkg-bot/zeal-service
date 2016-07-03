package com.zeal.worker.albums;

/**
 * Created by yang_shoulai on 2016/7/3.
 */
public abstract class AbstractPicturesPageResolver implements PicturesPageResolver {

    private PageAlbum pageAlbum;

    public AbstractPicturesPageResolver(PageAlbum pageAlbum){
        this.pageAlbum = pageAlbum;
    }


    @Override
    public PageAlbum getPageAlbum() {
        return pageAlbum;
    }
}
