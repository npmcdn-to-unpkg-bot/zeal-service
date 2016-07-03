package com.zeal.controller;

import com.zeal.worker.AlbumWorkerExecutor;
import com.zeal.worker.albums.meizitu.MeizituAlbumsPageResover;
import com.zeal.worker.albums.uuu9.UUU9AlbumsPageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yang_shoulai on 2016/7/3.
 */
@RequestMapping("/worker/album")
@Controller
public class AlbumWorkerController {

    @Autowired
    private AlbumWorkerExecutor albumWorkerExecutor;

    @RequestMapping(value = "/meizitu/{subUrl:.+}")
    public void meizituWorker(@PathVariable(value = "subUrl") String subUrl) {
        albumWorkerExecutor.execute(new MeizituAlbumsPageResover(subUrl));
    }

    @RequestMapping(value = "/uuu9/{subUrl:.+}")
    public void uuu9Worker(@PathVariable(value = "subUrl") String subUrl) {
        albumWorkerExecutor.execute(new UUU9AlbumsPageResolver(subUrl));
    }

}
