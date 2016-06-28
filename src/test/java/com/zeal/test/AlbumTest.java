package com.zeal.test;

import com.zeal.common.PagedList;
import com.zeal.dao.AlbumDao;
import com.zeal.dao.PictureDao;
import com.zeal.entity.Album;
import com.zeal.entity.Picture;
import com.zeal.entity.UserInfo;
import com.zeal.service.AlbumService;
import com.zeal.service.UserInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private UserInfoService userInfoService;


    @Resource
    private AlbumService albumService;

    @Resource
    private PictureDao pictureDao;

    @Test
    public void save() {
        Album album = new Album();
        UserInfo userInfo = userInfoService.find(1);
        album.setUserInfo(userInfo);
        album.setCreateDate(new Date());
        album.setUpdateDate(new Date());
        album.setName("AlbumOne");
        albumDao.save(album);

        List<Picture> pictures = new ArrayList<>();
        Picture picture1 = new Picture();
        picture1.setDescription("");
        picture1.setUrl("http://www.picture.com/1");
        picture1.setAlbum(album);
        pictureDao.save(picture1);

        Picture picture2 = new Picture();
        picture2.setDescription("");
        picture2.setUrl("http://www.picture.com/2");
        picture2.setAlbum(album);
        pictureDao.save(picture2);

        //pictures.add(picture1);
        //pictures.add(picture2);
        //album.setPictures(pictures);


        Picture picture3 = pictureDao.find(1L);
        System.out.println(picture3.getAlbum());
        PagedList<Album> pagedList = albumService.page(1, 20);
        Assert.assertTrue(pagedList != null);
    }


}
