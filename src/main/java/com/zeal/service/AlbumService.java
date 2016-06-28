package com.zeal.service;

import com.zeal.common.PagedList;
import com.zeal.entity.Album;

import java.util.List;

/**
 * Created by Administrator on 6/28/2016.
 */
public interface AlbumService {

    PagedList<Album> page(int page, int pageSize);

    Album find(long id);

    List<Album> findAll();
}
