package com.zeal.test;

import com.zeal.common.PagedList;
import com.zeal.dao.AlbumDao;
import com.zeal.dao.PictureDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import com.zeal.entity.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
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

    @Test
    public void save() {

        Album album = new Album();
        UserInfo userInfo = userInfoDao.find(1L);
        album.setUserInfo(userInfo);
        album.setCreateDate(new Date());
        album.setUpdateDate(new Date());
        album.setName("AlbumOne");
        albumDao.insert(album);

        List<Picture> pictures = new ArrayList<>();
        Picture picture1 = new Picture();
        picture1.setDescription("");
        picture1.setUrl("http://www.worker.com/1");
        picture1.setAlbum(album);
        pictureDao.insert(picture1);

        Picture picture2 = new Picture();
        picture2.setDescription("");
        picture2.setUrl("http://www.worker.com/2");
        picture2.setAlbum(album);
        pictureDao.insert(picture2);
        //pictures.add(picture1);
        //pictures.add(picture2);
        //album.setPictures(pictures);


        Picture picture3 = pictureDao.find(1L);
        PagedList<Album> pagedList = albumDao.paged(1, 20);
        Assert.assertTrue(pagedList != null);
    }


    @Test
    public void batchSave() {

    }

}
