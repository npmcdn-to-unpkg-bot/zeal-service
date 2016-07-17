package com.zeal.service;

import com.zeal.entity.AlbumAuthorAppreciation;

import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/16.
 */
public interface AlbumAuthorAppreciationService {


    void create(long appreciator, long appreciated);

    void delete(long appreciator, long appreciated);

    List<AlbumAuthorAppreciation> find(long appreciator, long appreciated);

    void delete(long recordId);
}
