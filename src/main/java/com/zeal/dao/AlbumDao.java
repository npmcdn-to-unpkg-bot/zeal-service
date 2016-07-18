package com.zeal.dao;

import com.zeal.common.PagedList;
import com.zeal.entity.Album;
import com.zeal.http.response.album.AlbumInfo;
import com.zeal.http.response.album.AlbumTagInfo;
import com.zeal.http.response.album.PagedAlbumInfo;
import com.zeal.http.response.album.PictureInfo;
import com.zeal.utils.ConfigureUtils;
import org.springframework.expression.spel.ast.LongLiteral;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 6/28/2016.
 */
@Repository
public class AlbumDao extends AbstractBaseDao<Album> {

    /**
     * 根据创建日期倒叙获取所有相册信息
     *
     * @return 相册信息列表
     */
    @Override
    public List<Album> findAll() {
        return this.entityManager().createQuery("SELECT o FROM Album o ORDER BY o.createDate desc ", Album.class).getResultList();
    }


    /**
     * 按日期倒敘获取所有相册信息
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PagedList<Album> paged(int page, int size) {
        List<Album> list = this.entityManager().createQuery("SELECT o FROM Album o ORDER BY o.createDate desc", Album.class).setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
        PagedList<Album> pagedList = new PagedList<>();
        pagedList.setList(list);
        pagedList.setTotalSize(countAll());
        pagedList.setPage(page);
        pagedList.setSize(size);
        return pagedList;
    }

    /**
     * 也分获取用户下的发布或者未发布相册
     *
     * @param author    作者ID
     * @param published 是否发布
     * @param page      分页页码 从 1 开始
     * @param pageSize  分页每页数量
     * @return 分页信息
     */
    public PagedList<Album> pagedByAuthorAndPublishStatus(long author, boolean published, int page, int pageSize) {
        TypedQuery<Album> query = this.entityManager().createQuery("SELECT o FROM Album o where o.userInfo.id = :userInfoId and o.isPublished = :isPublished ORDER BY o.createDate desc ", Album.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter("userInfoId", author);
        query.setParameter("isPublished", published);
        PagedList<Album> pagedList = new PagedList<>();
        pagedList.setList(query.getResultList());
        pagedList.setSize(pageSize);
        pagedList.setPage(page);
        pagedList.setTotalSize(countByUserInfoIdAndPublishStatus(author, published));
        return pagedList;
    }

    /**
     * 获取用户下所有相册的数量
     *
     * @return
     */
    public long countByUserInfoIdAndPublishStatus(Long userInfoId, boolean published) {
        TypedQuery<Long> query = this.entityManager().createQuery("SELECT count(o) FROM Album o where o.userInfo.id = :userInfoId and o.isPublished = :isPublished ORDER BY o.createDate desc ", Long.class);
        query.setParameter("userInfoId", userInfoId);
        query.setParameter("isPublished", published);
        return query.getSingleResult();
    }


    /**
     * 根据相册ID和用户ID查询相册信息
     *
     * @param id
     * @param userId
     * @return
     */
    public Album findByIdAndUserId(long id, long userId) {
        TypedQuery<Album> query = this.entityManager().createQuery("SELECT o FROM Album o where o.userInfo.id = :userInfoId and o.id = :id ORDER BY o.createDate desc ", Album.class);
        query.setParameter("userInfoId", userId);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    /**
     * 根据相册发布状态tag获取相册列表信息
     *
     * @param page          开始页码 从 1 开始
     * @param pageSize      每页数量
     * @param published     是否发布
     * @param tagId         归属的tag id
     * @param currentUserId 当前登录用户ID
     * @return
     */
    public PagedList<PagedAlbumInfo> pagedByPublishStatusAndTagId(int page, int pageSize, long tagId, boolean published, long currentUserId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT album.id AS id,");
        buffer.append("album. NAME AS NAME,");
        buffer.append("album.description AS description,");
        buffer.append("user_info.id as author,");
        buffer.append("user_info.nick_name as authorName,");
        buffer.append("(SELECT id from t_picture where album = album.id ORDER BY id LIMIT 1) as thumbnail_pic_id,");
        buffer.append("(SELECT count(1) from t_album_collection where album = album.id) as collectionCount,");
        buffer.append("(SELECT count(1) from t_album_author_appreciation where appreciated = album.user_info) as authorAppreciationCount, ");
        buffer.append("(SELECT count(1) from t_album_author_appreciation where appreciated = album.user_info and appreciator = :appreciator) as userAppreciationCount, ");
        buffer.append("(SELECT count(1) from t_album_collection where  album = album.id and user_info = :collector) as userCollectCount ");
        buffer.append("FROM t_album album ");
        buffer.append("LEFT JOIN t_user_info as user_info on user_info.id = album.user_info ");
        buffer.append("where album.id in (select album_id from t_album_tags where album_tag_id = :tagId) and album.published = :publishStatus ");
        buffer.append("ORDER BY album.publish_date desc LIMIT :first,:max");

        Query query = this.entityManager().createNativeQuery(buffer.toString());
        query.setParameter("tagId", tagId);
        query.setParameter("publishStatus", published ? 1 : 0);
        query.setParameter("first", (page - 1) * pageSize);
        query.setParameter("max", pageSize);
        query.setParameter("appreciator", currentUserId);
        query.setParameter("collector", currentUserId);

        List list = query.getResultList();
        List<PagedAlbumInfo> pagedAlbumInfos = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Object object : list) {
                if (object instanceof Object[]) {
                    Object[] array = (Object[]) object;
                    PagedAlbumInfo pagedAlbumInfo = new PagedAlbumInfo();
                    pagedAlbumInfo.setId(((BigInteger) array[0]).longValue());
                    pagedAlbumInfo.setName((String) array[1]);
                    pagedAlbumInfo.setDescription((String) array[2]);
                    pagedAlbumInfo.setAuthor(((BigInteger) array[3]).longValue());
                    pagedAlbumInfo.setAuthorName((String) array[4]);
                    pagedAlbumInfo.setThumbnail(ConfigureUtils.getAlbumServer() + "/resize/" + ((BigInteger) array[5]).longValue() + "?width=480&height=480");
                    pagedAlbumInfo.setCollectionCount(((BigInteger) array[6]).longValue());
                    pagedAlbumInfo.setAuthorAppreciationCount(((BigInteger) array[7]).longValue());
                    pagedAlbumInfo.setAuthorAppreciated(((BigInteger) array[8]).longValue() > 0);
                    pagedAlbumInfo.setCollected(((BigInteger) array[9]).longValue() > 0);
                    //TODO 获取作者头像
                    pagedAlbumInfo.setAuthorPhoto("/zeal/resources/app/icons/photo.jpg");
                    pagedAlbumInfos.add(pagedAlbumInfo);
                }
            }
        }
        PagedList<PagedAlbumInfo> pagedList = new PagedList<>();
        pagedList.setList(pagedAlbumInfos);
        pagedList.setSize(pageSize);
        pagedList.setPage(page);

        query = this.entityManager().createNativeQuery("SELECT count(1) FROM t_album where id in (select album_id from t_album_tags where album_tag_id = :tagId) and published = :publishStatus");
        query.setParameter("tagId", tagId);
        query.setParameter("publishStatus", published ? 1 : 0);
        pagedList.setTotalSize(Long.valueOf(query.getSingleResult().toString()));
        /*TypedQuery<Album> query = this.entityManager().createQuery("SELECT o FROM Album o inner join o.albumTags t where o.isPublished = :isPublished and t.id = :tagId ORDER BY o.publishDate desc ", Album.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter("isPublished", published);
        query.setParameter("tagId", tagId);
        */
        return pagedList;
    }

    public PagedList<Album> pagedByUserInfoIdAndKeywordLike(long userInfoId, int page, int pageSize, String keyword, int publishedState) {
        TypedQuery<Album> query;
        if (publishedState < 0) {
            query = this.entityManager().createQuery("SELECT o FROM Album o where o.userInfo.id = :userInfoId and (o.name like :keyword or o.description like :keyword) ORDER BY o.updateDate desc ", Album.class);
        } else if (publishedState == 0) {
            query = this.entityManager().createQuery("SELECT o FROM Album o where o.userInfo.id = :userInfoId and o.isPublished = :published and (o.name like :keyword or o.description like :keyword) ORDER BY o.updateDate desc ", Album.class);
        } else {
            query = this.entityManager().createQuery("SELECT o FROM Album o where o.userInfo.id = :userInfoId and o.isPublished = :published and (o.name like :keyword or o.description like :keyword) ORDER BY o.publishDate desc ", Album.class);
        }
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter("userInfoId", userInfoId);
        query.setParameter("keyword", "%" + keyword + "%");
        if (publishedState >= 0) {
            query.setParameter("published", publishedState > 0);
        }
        PagedList<Album> pagedList = new PagedList<>();
        pagedList.setList(query.getResultList());
        pagedList.setSize(pageSize);
        pagedList.setPage(page);
        pagedList.setTotalSize(countByUserInfoIdAndKeywordLike(userInfoId, keyword, publishedState));
        return pagedList;

    }

    private long countByUserInfoIdAndKeywordLike(long userInfoId, String keyword, int publishedState) {
        TypedQuery<Long> query;
        if (publishedState < 0) {
            query = this.entityManager().createQuery("SELECT count(o) FROM Album o where o.userInfo.id = :userInfoId and (o.name like :keyword or o.description like :keyword)", Long.class);
        } else {
            query = this.entityManager().createQuery("SELECT count(o) FROM Album o where o.userInfo.id = :userInfoId and o.isPublished = :published and (o.name like :keyword or o.description like :keyword)", Long.class);
        }
        query.setParameter("userInfoId", userInfoId);
        query.setParameter("keyword", "%" + keyword + "%");
        if (publishedState >= 0) {
            query.setParameter("published", publishedState > 0);
        }
        return query.getSingleResult();
    }


    /**
     * 获取相册的详细信息
     * 包含相册的评论数、收藏数和获赞数
     *
     * @param id            相册ID
     * @param currentUserId 当前登录用户ID
     * @return
     */
    public AlbumInfo findDetails(long id, long currentUserId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT album.id as album_id,");
        buffer.append("album.name as album_name,");
        buffer.append("album.create_date,");
        buffer.append("album.description,");
        buffer.append("album.published,");
        buffer.append("album.publish_date,");
        buffer.append("album.user_info AS auhor_id,");
        buffer.append("(select nick_name from t_user_info where id = album.user_info) as author_name,");
        buffer.append("(select count(1) from t_album_collection where album = album.id) as collection_count,");
        buffer.append("(select count(1) from t_album_appreciation where album = album.id) as appreciation_count,");
        buffer.append("(select count(1) from t_album_collection where album = album.id and user_info = :currentUserId) as user_collected_count,");
        buffer.append("(select count(1) from t_album_appreciation where album = album.id and user_info = :currentUserId) as user_appreciated_count");
        buffer.append(" FROM t_album as album where album.id = :albumId");
        Query query = this.entityManager().createNativeQuery(buffer.toString());
        query.setParameter("currentUserId", currentUserId);
        query.setParameter("albumId", id);
        List list = query.getResultList();
        AlbumInfo albumInfo = null;
        if (!list.isEmpty()) {
            Object object = list.get(0);
            if (object instanceof Object[]) {
                Object[] array = (Object[]) object;
                albumInfo = new AlbumInfo();
                albumInfo.setId(((BigInteger) array[0]).longValue());
                albumInfo.setName((String) array[1]);
                albumInfo.setCreateDate((Date) array[2]);
                albumInfo.setDescription((String) array[3]);
                albumInfo.setPublished((Boolean) array[4]);
                albumInfo.setPublishDate((Date) array[5]);
                albumInfo.setAuthor(((BigInteger) array[6]).longValue());
                albumInfo.setAuthorName((String) array[7]);
                albumInfo.setCollectionCount(((BigInteger) array[8]).intValue());
                albumInfo.setAppreciationCount(((BigInteger) array[9]).intValue());
                albumInfo.setCollected(array[10] != null && ((BigInteger) array[10]).intValue() > 0);
                albumInfo.setAppreciated(array[11] != null && ((BigInteger) array[11]).intValue() > 0);
            }
        }
        if (albumInfo != null) {
            List<PictureInfo> pictures = new ArrayList<>();
            albumInfo.setPictures(pictures);
            query = this.entityManager().createNativeQuery("SELECT id, url, description from t_picture where album = :id ORDER BY id");
            query.setParameter("id", id);
            list = query.getResultList();
            if (!list.isEmpty()) {
                for (Object object : list) {
                    if (object instanceof Object[]) {
                        Object[] array = (Object[]) object;
                        PictureInfo picture = new PictureInfo();
                        picture.setId(((BigInteger) array[0]).longValue());
                        picture.setUrl(ConfigureUtils.getAlbumServer() + ((BigInteger) array[0]).longValue());
                        picture.setDescription(((String) array[2]));
                        pictures.add(picture);
                    }
                }
            }

            List<AlbumTagInfo> tags = new ArrayList<>();
            albumInfo.setTags(tags);
            query = this.entityManager().createNativeQuery("SELECT tag.id, tag.name, tag.parent from t_album_tag as tag where tag.id in (select album_tag_id from t_album_tags where album_id = :id) ORDER BY id");
            query.setParameter("id", id);
            list = query.getResultList();
            if (!list.isEmpty()) {
                for (Object object : list) {
                    if (object instanceof Object[]) {
                        Object[] array = (Object[]) object;
                        AlbumTagInfo tag = new AlbumTagInfo();
                        tag.setId(((BigInteger) array[0]).longValue());
                        tag.setName(((String) array[1]));
                        tags.add(tag);
                    }
                }
            }
        }
        return albumInfo;
    }

    /**
     * 获取相册列表展示时需要的信息
     *
     * @param id
     * @return
     */
    public PagedAlbumInfo findPagedAlbumInfo(long id) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT album.id AS id,");
        buffer.append("album. NAME AS NAME,");
        buffer.append("album.description AS description,");
        buffer.append("user_info.id as author,");
        buffer.append("user_info.nick_name as authorName,");
        buffer.append("(SELECT concat('" + ConfigureUtils.getAlbumServer() + "', id) from t_picture where album = album.id ORDER BY id LIMIT 1) as thumbnail,");
        buffer.append("(SELECT count(1) from t_album_collection where album = album.id) as collectionCount,");
        buffer.append("(SELECT count(1) from t_album_author_appreciation where appreciated = album.user_info) as authorAppreciationCount ");
        buffer.append("FROM t_album album ");
        buffer.append("LEFT JOIN t_user_info as user_info on user_info.id = album.user_info ");
        buffer.append("where album.id = :id");
        Query query = this.entityManager().createNativeQuery(buffer.toString());
        query.setParameter("id", id);
        Object object = query.getSingleResult();
        Object[] array = (Object[]) object;
        PagedAlbumInfo pagedAlbumInfo = new PagedAlbumInfo();
        pagedAlbumInfo.setId(((BigInteger) array[0]).longValue());
        pagedAlbumInfo.setName((String) array[1]);
        pagedAlbumInfo.setDescription((String) array[2]);
        pagedAlbumInfo.setAuthor(((BigInteger) array[3]).longValue());
        pagedAlbumInfo.setAuthorName((String) array[4]);
        pagedAlbumInfo.setThumbnail((String) array[5]);
        pagedAlbumInfo.setCollectionCount(((BigInteger) array[6]).longValue());
        pagedAlbumInfo.setAuthorAppreciationCount(((BigInteger) array[7]).longValue());
        return pagedAlbumInfo;
    }

}
