package com.zeal.activiti.service;

import com.zeal.service.AlbumCollectionService;
import com.zeal.service.AlbumService;
import com.zeal.vo.album.AlbumCollectionVO;
import com.zeal.vo.album.AlbumVO;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 7/21/2016.
 */
@Service
public class ActivitiAlbumService {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumCollectionService albumCollectionService;

    @Autowired
    RuntimeService runtimeService;

    public void notifyAllCollectors(long albumId) {
        AlbumVO albumVO = albumService.findBasic(albumId);
        List<AlbumCollectionVO> albumCollectionVOList = albumCollectionService.findByAlbumIdEquals(albumId);
        if (!albumCollectionVOList.isEmpty()) {
            for (AlbumCollectionVO albumCollectionVO : albumCollectionVOList) {
                Map<String, Object> params = new HashMap<>();
                params.put("albumId", albumId);
                params.put("albumName", albumVO.getName());
                params.put("userInfoId", albumCollectionVO.getCollector().getId());
                runtimeService.startProcessInstanceByKey("album-unpublish-handle", params);
            }
        }
    }


}
