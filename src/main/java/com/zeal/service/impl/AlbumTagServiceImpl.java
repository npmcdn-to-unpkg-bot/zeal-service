package com.zeal.service.impl;

import com.zeal.dao.AlbumTagDao;
import com.zeal.entity.AlbumTag;
import com.zeal.service.AlbumTagService;
import com.zeal.vo.album.AlbumTagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/8.
 */
@Service
public class AlbumTagServiceImpl implements AlbumTagService {


    @Autowired
    private AlbumTagDao albumTagDao;


    /**
     * 获取所有子TAG
     *
     * @param parentId
     * @return
     */
    @Override
    public List<AlbumTagVO> findChildrenByTagId(long parentId) {
        List<AlbumTagVO> tags = new ArrayList<>();
        List<AlbumTag> albumTags = albumTagDao.findChildrenById(parentId);
        for (AlbumTag albumTag : albumTags) {
            tags.add(new AlbumTagVO(albumTag));
        }
        return tags;
    }

    @Override
    public List<AlbumTagVO> findAllChildren() {
        List<AlbumTagVO> tags = new ArrayList<>();
        List<AlbumTag> albumTags = albumTagDao.findAllChildren();
        for (AlbumTag albumTag : albumTags) {
            tags.add(new AlbumTagVO(albumTag));
        }
        return tags;
    }
}
