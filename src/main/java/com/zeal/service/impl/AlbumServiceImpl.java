package com.zeal.service.impl;

import com.zeal.common.PagedList;
import com.zeal.dao.*;
import com.zeal.entity.*;
import com.zeal.exception.BizException;
import com.zeal.http.response.album.AlbumInfo;
import com.zeal.http.response.album.PagedAlbumInfo;
import com.zeal.service.AlbumService;
import com.zeal.utils.*;
import com.zeal.vo.album.AlbumVO;
import com.zeal.worker.albums.PageAlbum;
import com.zeal.worker.albums.PagePicture;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 6/28/2016.
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private AlbumTagDao albumTagDao;

    @Autowired
    private AlbumCollectionDao albumCollectionDao;

    @Autowired
    private AlbumAppreciationDao albumAppreciationDao;

    @Autowired
    private AlbumAuthorAppreciationDao albumAuthorAppreciationDao;

    /**
     * 获取相册的详细信息
     *
     * @param id            相册ID
     * @param currentUserId 当前用登录户ID
     * @return
     */
    @Override
    public AlbumInfo findDetails(long id, long currentUserId) {
        return albumDao.findDetails(id, currentUserId);
    }

    /**
     * 获取相册几本信息
     *
     * @param id 相册ID
     * @return
     */
    @Override
    public AlbumVO findBasic(long id) {
        Album album = albumDao.find(id);
        if (album == null) return null;
        return new AlbumVO(album);
    }


    @Override
    public void publish(long id, long userId) {
        Album album = albumDao.find(id);
        album.setPublished(true);
        album.setPublishDate(new Date());
        album.setUpdateDate(new Date());
        albumDao.update(album);
    }

    @Override
    public void unPublish(long id, long userId) {
        Album album = albumDao.find(id);
        album.setPublished(false);
        album.setPublishDate(null);
        album.setUpdateDate(new Date());
        albumDao.update(album);
    }

    /**
     * 分页获取相册信息
     *
     * @param page          分页页码
     * @param pageSize      每页数量
     * @param tagId         相册tag
     * @param currentUserId 当前登录用户ID
     * @return
     */
    @Override
    public PagedList<PagedAlbumInfo> published(int page, int pageSize, long tagId, long currentUserId) {
        return albumDao.pagedByPublishStatusAndTagId(page, pageSize, tagId, true, currentUserId);
    }

    /**
     * 分页获取相册作者的相册信息
     *
     * @param page          当前页码
     * @param pageSize      每页数量
     * @param authorId      相册作者ID
     * @param keyword       关键字
     * @param currentUserId 当前登录用户ID
     * @param state         相册发布状态 -1，全部；已发布 0； 未发布 1
     * @return
     */
    @Override
    public PagedList<AlbumInfo> pagedByUserInfoIdAndKeywordLike(int page, int pageSize, long authorId, String keyword, long currentUserId, int state) {
        PagedList<Album> pagedList = albumDao.pagedByUserInfoIdAndKeywordLike(authorId, page, pageSize, keyword, state);
        return convert(pagedList, currentUserId);
    }

    @Override
    @Transactional
    public void delete(long id, long userInfoId) {
        Album album = albumDao.find(id);
        List<Picture> pictures = album.getPictures();
        if (pictures != null && !pictures.isEmpty()) {
            for (Picture picture : pictures) {
                pictureDao.delete(picture);
            }
        }
        albumDao.delete(album);
        FileUtils.deleteFile(AlbumUtils.getAlbumFolder(album).getPath());
    }

    @Override
    @Transactional
    public void create(String name, String description, List<MultipartFile> files, long userInfoId) {
        UserInfo userInfo = userInfoDao.find(userInfoId);
        List<File> pictureFiles = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        try {
            Album album = new Album();
            album.setName(name);
            album.setDescription(description);
            album.setPublished(false);
            album.setCreateDate(new Date());
            album.setUpdateDate(new Date());
            album.setUserInfo(userInfo);
            albumDao.insert(album);
            resolvePicturesFromMultipartFiles(pictureFiles, pictures, files, userInfo.getId(), album);
            if (!pictures.isEmpty()) {
                pictureDao.batchInsert(pictures);
            }
        } catch (Exception e) {
            LogUtils.error(this.getClass(), "", e);
            if (!pictureFiles.isEmpty()) {
                for (File file : pictureFiles) {
                    FileUtils.deleteFile(file.getPath());
                }
            }
            throw new BizException("创建相册失败");
        }

    }

    /**
     * 更新相册信息
     *
     * @param id            相册ID
     * @param name          相册名称
     * @param description   相册说明
     * @param deleteIdArray 需要删除的图片列表
     * @param newFiles      需要添加的图片
     * @param userInfoId    用户ID
     */
    @Override
    @Transactional
    public void update(long id, String name, String description, int[] deleteIdArray, List<MultipartFile> newFiles, int[] tags, long userInfoId) {
        Album album = albumDao.find(id);
        if (!StringUtils.isEmpty(name)) {
            album.setName(name);
        }
        album.setDescription(description);
        Set<AlbumTag> albumTags = new HashSet<>();
        if (tags != null && tags.length > 0) {
            for (int tagId : tags) {
                albumTags.add(albumTagDao.find(tagId));
            }
        }
        album.setAlbumTags(albumTags);
        if (deleteIdArray != null && deleteIdArray.length > 0) {
            for (int deleteId : deleteIdArray) {
                Picture picture = pictureDao.find(deleteId);
                AlbumUtils.deleteDiskFile(picture);
                pictureDao.delete(picture);
            }
        }
        List<File> pictureFiles = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        try {
            resolvePicturesFromMultipartFiles(pictureFiles, pictures, newFiles, userInfoId, album);
            if (!pictures.isEmpty()) {
                pictureDao.batchInsert(pictures);
            }
            albumDao.update(album);
        } catch (Exception e) {
            LogUtils.error(this.getClass(), "", e);
            if (!pictureFiles.isEmpty()) {
                for (File file : pictureFiles) {
                    FileUtils.deleteFile(file.getPath());
                }
            }
            throw new BizException("更新相册失败");
        }
    }


    @Override
    @Transactional
    public void save(PageAlbum pageAlbum, UserInfo userInfo) {
        List<PagePicture> pictures = pageAlbum.pictures;
        Album album = new Album();
        album.setName(pageAlbum.name);
        album.setUserInfo(userInfo);
        album.setUpdateDate(new Date());
        album.setCreateDate(new Date());
        album.setPublished(false);

        if (pageAlbum.albumTags != null && !pageAlbum.albumTags.isEmpty()) {
            Set<AlbumTag> tags = new HashSet<>();
            for (Long tagId : pageAlbum.albumTags) {
                AlbumTag tag = albumTagDao.find(tagId);
                if (tag != null) {
                    tags.add(albumTagDao.find(tagId));
                }
            }
            album.setAlbumTags(tags);
        }
        albumDao.insert(album);
        if (pictures != null) {
            for (PagePicture picture : pictures) {
                Picture pic = new Picture();
                pic.setAlbum(album);
                String name = picture.src.substring(picture.src.lastIndexOf("/") + 1, picture.src.length());
                pic.setUrl(userInfo.getId() + File.separator + album.getId() + File.separator + name);
                pictureDao.insert(pic);
            }
        }
        List<File> files = download(pictures, userInfo.getId(), album.getId());
        if (files.isEmpty()) {
            throw new BizException("保存失败");
        }
    }

    /**
     * 创建相册的缩略图
     *
     * @param albumId
     * @return
     */
    @Override
    public File createThumbnail(long albumId) {
        Album album = albumDao.find(albumId);
        File albumFolder = AlbumUtils.getAlbumFolder(album);
        File thumbnailFile = FileUtils.createFile(albumFolder.getPath() + File.separator + "thumbnail" + File.separator + "thumbnail.jpeg");
        List<Picture> pictures = album.getPictures();
        File picFile = AlbumUtils.getDiskFile(pictures.get(0));
        ImageUtils.createThumbnail(picFile, thumbnailFile);
        return thumbnailFile;
    }

    /**
     * 获取相册的缩略图
     *
     * @param albumId
     * @return
     */
    @Override
    public File getThumbnail(long albumId) {
        Album album = albumDao.find(albumId);
        File albumFolder = AlbumUtils.getAlbumFolder(album);
        File file = new File(albumFolder.getPath() + File.separator + "thumbnail" + File.separator + "thumbnail.jpeg");
        if (!file.exists()) return null;
        return file;
    }


    /**
     * 下载图片
     *
     * @param pictures
     * @param userInfoId
     * @param albumId
     * @return
     */
    private List<File> download(List<PagePicture> pictures, long userInfoId, long albumId) {
        List<File> downloads = new ArrayList<>();
        try {
            for (PagePicture pagePicture : pictures) {
                String name = pagePicture.src.substring(pagePicture.src.lastIndexOf("/") + 1, pagePicture.src.length());
                File file = DownloadUtils.downloadFile(pagePicture.src, ConfigureUtils.getAlbumRepository() + userInfoId + File.separator + albumId + File.separator + name);
                downloads.add(file);
            }
        } catch (Exception e) {
            if (!downloads.isEmpty()) {
                FileUtils.deleteFile(downloads.get(0).getParentFile().getPath());
            }
            downloads.clear();
        }
        return downloads;
    }


    private AlbumInfo convert(Album album, long currentUserId) {
        //TODO 添加相册的评论数和被赞数
        AlbumInfo albumInfo = new AlbumInfo();
        BeanUtils.copyProperties(new AlbumVO(album), albumInfo);
        Set<AlbumCollection> collections = album.getAlbumCollections();
        albumInfo.setCollectionCount(collections.size());
        albumInfo.setAppreciationCount(album.getAlbumAppreciations().size());
        albumInfo.setCommentCount(0);
        albumInfo.setCollected(currentUserId > 0 && !albumCollectionDao.findByUserInfoIdAndAlbumId(currentUserId, album.getId()).isEmpty());
        albumInfo.setAppreciated(currentUserId > 0 && !albumAppreciationDao.findByUserInfoIdAndAlbumId(currentUserId, album.getId()).isEmpty());
        return albumInfo;
    }


    private PagedList<AlbumInfo> convert(PagedList<Album> pagedList, long currentUserId) {
        PagedList<AlbumInfo> list = new PagedList<>();
        list.setPage(pagedList.getPage());
        list.setSize(pagedList.getSize());
        list.setTotalSize(pagedList.getTotalSize());
        List<AlbumInfo> albums = new ArrayList<>();
        for (Album album : pagedList.getList()) {
            albums.add(convert(album, currentUserId));
        }
        list.setList(albums);
        return list;
    }


    private void resolvePicturesFromMultipartFiles(List<File> files,
                                                   List<Picture> pictures,
                                                   List<MultipartFile> multipartFiles,
                                                   long userInfoId, Album album) throws IOException {
        if (multipartFiles != null && multipartFiles.size() > 0) {
            for (MultipartFile multipartFile : multipartFiles) {
                String pictureFullPath = ConfigureUtils.getAlbumRepository() + userInfoId + File.separator + album.getId() + File.separator + multipartFile.getOriginalFilename();
                File pictureFile = FileUtils.createFile(pictureFullPath);
                multipartFile.transferTo(pictureFile);
                files.add(pictureFile);
                Picture picture = new Picture();
                picture.setAlbum(album);
                picture.setUrl(userInfoId + "/" + album.getId() + "/" + multipartFile.getOriginalFilename());
                pictures.add(picture);
            }
        }
    }
}
