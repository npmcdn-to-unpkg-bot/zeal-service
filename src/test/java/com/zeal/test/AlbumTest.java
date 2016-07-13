package com.zeal.test;

import com.zeal.common.PagedList;
import com.zeal.dao.AlbumDao;
import com.zeal.dao.PictureDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import com.zeal.entity.UserInfo;
import com.zeal.service.AlbumService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void deleteDuplicateAlbums() {
        TypedQuery<Album> query = entityManager.createQuery("select o from Album o where o.id >= 729 and o.id <= 5426", Album.class);
        List<Album> list = query.getResultList();
        for (Album album : list) {
            albumService.delete(album.getId(), 1L);
        }
    }


}
