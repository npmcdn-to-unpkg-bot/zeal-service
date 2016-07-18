package com.zeal.test;

import com.zeal.dao.AlbumDao;
import com.zeal.dao.PictureDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.service.AlbumService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        albumDao.findPagedAlbumInfo(1);
    }


}
