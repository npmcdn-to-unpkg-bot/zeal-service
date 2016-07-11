package com.zeal.controller;

import com.zeal.worker.AlbumWorkerExecutor;
import com.zeal.worker.albums.meizitu.MeizituAlbumsPageResover;
import com.zeal.worker.albums.t27270.T27270AlbumsPageResolver;
import com.zeal.worker.albums.taohua.TaoHuaAlbumsPageResolver;
import com.zeal.worker.albums.uuu9.UUU9AlbumsPageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    //http://localhost:8080/zeal/worker/album/t27270?subUrl=word%2fdongwushijie
    //http://localhost:8080/zeal/worker/album/t27270?subUrl=ent%2fmeinvtupian
    @RequestMapping(value = "/t27270")
    public void t27270Worker(@RequestParam(value = "subUrl") String subUrl) {
        albumWorkerExecutor.execute(new T27270AlbumsPageResolver(subUrl));
    }


    @RequestMapping(value = "/taohua/{subUrl:.+}")
    public void taoHuaWorker(@PathVariable(value = "subUrl") String subUrl) {
        albumWorkerExecutor.execute(new TaoHuaAlbumsPageResolver(subUrl));
    }


}
