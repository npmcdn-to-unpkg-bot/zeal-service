package com.zeal.test;

import com.zeal.worker.AlbumWorkerExecutor;
import com.zeal.worker.meizitu.MeizituAlbumWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yang_shoulai on 2016/6/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/applicationContext.xml")
public class MeizituAlbumWorkerTest {


    @Autowired
    private AlbumWorkerExecutor albumWorkerExecutor;


    @Test
    public void test() {
        //albumWorkerExecutor.execute(new MeizituAlbumWorker("xinggan.html"));

        albumWorkerExecutor.execute(new MeizituAlbumWorker("sifang.html"));

        albumWorkerExecutor.execute(new MeizituAlbumWorker("qingchun.html"));

    }


}
