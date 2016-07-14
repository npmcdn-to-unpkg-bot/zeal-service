package com.zeal.test;

import com.zeal.dao.AlbumDao;
import com.zeal.dao.PictureDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.http.response.album.AlbumQueryResult;
import com.zeal.service.AlbumService;
import com.zeal.vo.album.AlbumVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Administrator on 6/28/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/applicationContext.xml")
public class AlbumTest {

    @Resource
    private AlbumDao albumDao;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private PictureDao pictureDao;

    @Resource
    private AlbumService albumService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deleteDuplicateAlbums() {
        AlbumVO albumVO = albumService.find(1L);
        AlbumQueryResult result = new AlbumQueryResult();
        BeanUtils.copyProperties(albumVO, result);
        Assert.assertTrue(result.getId() == 1);
    }


}
