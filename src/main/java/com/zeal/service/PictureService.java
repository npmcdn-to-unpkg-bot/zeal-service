package com.zeal.service;

import com.zeal.vo.album.PictureVO;

/**
 * Created by Administrator on 6/29/2016.
 */
public interface PictureService {

    PictureVO find(long id);

    void delete(long id, long userInfoId);
}
