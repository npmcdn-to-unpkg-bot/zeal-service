package com.zeal.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yang_shoulai on 2016/7/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/applicationContext.xml")
public class T27270AlbumWorkerTest {


    @Test
    public void test() {

        //albumWorkerExecutor.execute(new T27270AlbumWorker("sifang.html"));

        //albumWorkerExecutor.execute(new T27270AlbumWorker("qingchun.html"));

    }
}
