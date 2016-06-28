package com.zeal.service.impl;

import com.zeal.common.PagedList;
import com.zeal.dao.AlbumDao;
import com.zeal.entity.Album;
import com.zeal.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 6/28/2016.
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Override
    public PagedList<Album> page(int page, int pageSize) {
        if (page <= 0) page = 1;
        if (pageSize <= 0) pageSize = 10;
        return albumDao.paged(page, pageSize);
    }

    @Override
    public Album find(long id) {
        return albumDao.find(id);
    }

    @Override
    public List<Album> findAll() {
        return albumDao.findAll();
    }
}
