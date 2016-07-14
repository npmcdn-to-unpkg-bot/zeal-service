package com.zeal.service.impl;

import com.zeal.common.PagedList;
import com.zeal.dao.AlbumDao;
import com.zeal.dao.AlbumTagDao;
import com.zeal.dao.PictureDao;
import com.zeal.dao.UserInfoDao;
import com.zeal.entity.Album;
import com.zeal.entity.AlbumTag;
import com.zeal.entity.Picture;
import com.zeal.entity.UserInfo;
import com.zeal.exception.BizException;
import com.zeal.service.AlbumService;
import com.zeal.utils.*;
import com.zeal.vo.album.AlbumVO;
import com.zeal.worker.albums.PageAlbum;
import com.zeal.worker.albums.PagePicture;
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


    @Override
    public AlbumVO find(long id) {
        Album album = albumDao.find(id);
        if (album == null) return null;
        return new AlbumVO(album);
    }


    @Override
    public void publish(long id, long userId) {
        Album album = albumDao.find(id);
        album.setPublished(true);
        album.setPublishDate(new Date());
        albumDao.update(album);
    }

    @Override
    public void unPublish(long id, long userId) {
        Album album = albumDao.find(id);
        album.setPublished(false);
        album.setPublishDate(null);
        albumDao.update(album);
    }

    @Override
    public PagedList<AlbumVO> published(int page, int pageSize, long tagId) {
        return convert(albumDao.pagedByPublishStatus(page, pageSize, tagId, true));
    }


    @Override
    public PagedList<AlbumVO> pagedByUserInfoIdAndKeywordLike(int page, int pageSize, long userInfoId, String keyword) {
        return convert(albumDao.pagedByUserInfoIdAndKeywordLike(userInfoId, page, pageSize, keyword));
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
        FileUtils.deleteFile(getDiskFolder(album).getPath());
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
                PictureUtils.deleteDiskFile(picture);
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

    /**
     * 获取相册所在的硬盘文件夹
     *
     * @param album
     * @return
     */
    @Override
    public File getDiskFolder(Album album) {

        return new File(ConfigureUtils.getAlbumRepository() + album.getUserInfo().getId() + File.separator + album.getId());
    }

    /**
     * 获取相册所在的硬盘文件夹
     *
     * @param albumId
     * @return
     */
    @Override
    public File getDiskFolder(long albumId) {
        return getDiskFolder(albumDao.find(albumId));
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
        File albumFolder = getDiskFolder(album);
        File thumbnailFile = FileUtils.createFile(albumFolder.getPath() + File.separator + "thumbnail" + File.separator + "thumbnail.jpeg");
        List<Picture> pictures = album.getPictures();
        File picFile = PictureUtils.getDiskFile(pictures.get(0));
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
        File albumFolder = getDiskFolder(album);
        File file = new File(albumFolder.getPath() + File.separator + "thumbnail" + File.separator + "thumbnail.jpeg");
        if (!file.exists()) return null;
        return file;
    }

    /**
     * 获取用户的发布或者未发布相册的数量
     *
     * @param published
     * @param userId
     * @return
     */
    @Override
    public long countByPublishStatus(boolean published, long userId) {

        return albumDao.countByUserInfoIdAndPublishStatus(userId, published);
    }

    /**
     * 获取用户发布或者未发布的相册列表
     *
     * @param published
     * @param userId
     * @param page
     * @param pageSize  @return
     */
    @Override
    public PagedList<AlbumVO> findByPublishStatus(boolean published, long userId, int page, int pageSize) {

        return convert(albumDao.pagedByUserInfoIdAndPublishStatus(userId, published, page, pageSize));
    }

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

    private PagedList<AlbumVO> convert(PagedList<Album> albumPagedList) {
        PagedList<AlbumVO> albumVOPagedList = new PagedList<>();
        albumVOPagedList.setTotalSize(albumPagedList.getTotalSize());
        albumVOPagedList.setPage(albumPagedList.getPage());
        albumVOPagedList.setSize(albumPagedList.getSize());
        List<Album> albums = albumPagedList.getList();
        List<AlbumVO> albumVOs = new ArrayList<>();
        if (albums != null && !albums.isEmpty()) {
            for (Album album : albums) {
                albumVOs.add(new AlbumVO(album));
            }
        }
        albumVOPagedList.setList(albumVOs);
        return albumVOPagedList;
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
