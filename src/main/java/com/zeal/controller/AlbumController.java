package com.zeal.controller;

import com.zeal.common.PagedList;
import com.zeal.http.response.Response;
import com.zeal.http.response.album.AlbumAuthorInfo;
import com.zeal.http.response.album.AlbumInfo;
import com.zeal.http.response.album.AlbumTagInfo;
import com.zeal.http.response.album.PictureInfo;
import com.zeal.service.AlbumCollectionService;
import com.zeal.service.AlbumService;
import com.zeal.service.AuthorityCheckService;
import com.zeal.utils.SessionUtils;
import com.zeal.utils.StringUtils;
import com.zeal.vo.album.AlbumTagVO;
import com.zeal.vo.album.AlbumVO;
import com.zeal.vo.album.PictureVO;
import com.zeal.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 6/28/2016.
 */
@RequestMapping("/album")
@Controller
public class AlbumController extends AbstractController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AuthorityCheckService authorityCheckService;

    @Autowired
    private AlbumCollectionService albumCollectionService;

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response find(@PathVariable("id") long id, HttpServletRequest request) {
        authorityCheckService.checkAlbumReadAuthority(request, id);
        AlbumVO albumVO = albumService.find(id);
        AlbumInfo albumInfo = convert(albumVO, SessionUtils.getUserInfo(request));
        return new Response.Builder().success().result(albumInfo).build();
    }


    @RequestMapping(value = "/publish/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response publish(@PathVariable("id") long id, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        authorityCheckService.checkAlbumModifyAuthority(request, id);
        albumService.publish(id, userInfo.getId());
        return new Response.Builder().success().result(albumService.find(id)).build();
    }

    @RequestMapping(value = "/unpublish/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response unPublish(@PathVariable("id") long id, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        authorityCheckService.checkAlbumModifyAuthority(request, id);
        albumService.unPublish(id, userInfo.getId());
        return new Response.Builder().success().result(albumService.find(id)).build();
    }

    @RequestMapping(value = "/published", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response published(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                              @RequestParam(value = "tag", required = false, defaultValue = "-1") long tagId, HttpServletRequest request) {
        UserInfoVO userInfo = SessionUtils.getUserInfo(request);
        PagedList<AlbumVO> pagedList = albumService.published(page, pageSize, tagId);
        PagedList<AlbumInfo> list = new PagedList<>();
        list.setSize(pagedList.getSize());
        list.setPage(pagedList.getPage());
        list.setTotalSize(pagedList.getTotalSize());
        List<AlbumInfo> results = new ArrayList<>();
        for (AlbumVO albumVO : pagedList.getList()) {
            results.add(convert(albumVO, userInfo));
        }
        list.setList(results);
        return new Response.Builder().success().result(list).build();
    }

    @RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response delete(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        authorityCheckService.checkAlbumModifyAuthority(httpServletRequest, id);
        albumService.delete(id, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
    }

    @RequestMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response create(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "description", required = false) String description,
                           DefaultMultipartHttpServletRequest httpServletRequest) {
        List<MultipartFile> files = resolveMultipartFiles(httpServletRequest);
        albumService.create(name, description, files, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
    }

    @RequestMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response update(@RequestParam(value = "name") String name,
                           @RequestParam(value = "description", required = false) String description,
                           @RequestParam(value = "deletes", required = false) String deleteIdArray,
                           @RequestParam(value = "tags", required = false) String tagIdArray,
                           @RequestParam(value = "id") long id,
                           DefaultMultipartHttpServletRequest httpServletRequest) {
        authorityCheckService.checkAlbumModifyAuthority(httpServletRequest, id);
        List<MultipartFile> newFiles = resolveMultipartFiles(httpServletRequest);
        int[] deletes = stringToArray(deleteIdArray);
        int[] tags = stringToArray(tagIdArray);
        albumService.update(id, name, description, deletes, newFiles, tags, SessionUtils.getUserInfo(httpServletRequest).getId());
        return new Response.Builder().success().build();
    }

    /**
     * 相册封面图片
     *
     * @param id
     * @param response
     */
    @RequestMapping(value = "/thumbnail/{id}")
    public void thumbnail(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) {
        authorityCheckService.checkAlbumReadAuthority(request, id);
        File file = albumService.getThumbnail(id);
        if (file == null || !file.exists()) {
            file = albumService.createThumbnail(id);
        }
        returnFile(response, file);
    }


    private List<MultipartFile> resolveMultipartFiles(DefaultMultipartHttpServletRequest request) {
        List<MultipartFile> files = new ArrayList<>();
        MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
        if (map != null && !map.isEmpty()) {
            Collection<List<MultipartFile>> collection = map.values();
            if (!collection.isEmpty()) {
                for (List<MultipartFile> list : collection) {
                    files.addAll(list);
                }
            }
        }
        return files;
    }


    private int[] stringToArray(String ArrayStr) {
        int[] tags = null;
        if (!StringUtils.isEmpty(ArrayStr)) {
            String[] strs = ArrayStr.split(",");
            tags = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                tags[i] = Integer.valueOf(strs[i]);
            }
        }
        return tags;
    }

    private AlbumInfo convert(AlbumVO albumVO, UserInfoVO currentUser) {
        AlbumInfo albumInfo = new AlbumInfo();
        AlbumAuthorInfo author = new AlbumAuthorInfo();
        albumInfo.author = author;
        albumInfo.id = albumVO.getId();
        albumInfo.name = albumVO.getName();
        albumInfo.description = albumVO.getDescription();
        albumInfo.createDate = albumVO.getCreateDate();
        albumInfo.publishDate = albumVO.getPublishDate();
        albumInfo.isPublished = albumVO.isPublished();
        albumInfo.commentCount = 0;
        albumInfo.appreciationCount = 0;
        albumInfo.collectionCount = albumCollectionService.countByAlbumIdEquals(albumInfo.id);
        albumInfo.collected = currentUser != null && albumCollectionService.collected(currentUser.getId(), albumVO.getId());
        List<PictureInfo> pictures = new ArrayList<>();
        List<AlbumTagInfo> tags = new ArrayList<>();
        albumInfo.pictures = pictures;
        albumInfo.tags = tags;
        for (PictureVO pictureVO : albumVO.getPictures()) {
            PictureInfo pictureInfo = new PictureInfo();
            pictureInfo.id = pictureVO.getId();
            pictureInfo.url = pictureVO.getUrl();
            pictureInfo.description = pictureVO.getDescription();
            pictures.add(pictureInfo);
        }
        for (AlbumTagVO albumTagVO : albumVO.getTags()) {
            AlbumTagInfo albumTagInfo = new AlbumTagInfo();
            albumTagInfo.id = albumTagVO.getId();
            albumTagInfo.name = albumTagVO.getName();
            tags.add(albumTagInfo);
        }
        author.id = albumVO.getUserInfo().getId();
        author.nickName = albumVO.getUserInfo().getNickName();
        author.email = "412837184@qq.com";
        author.appreciationCount = 0;
        author.description = "";
        author.photo = "/zeal/resources/app/icons/photo.jpg";
        author.followerCount = 0;
        author.followingCount = 0;
        author.publishedCount = albumService.countByPublishStatus(true, author.id);
        return albumInfo;
    }

}
