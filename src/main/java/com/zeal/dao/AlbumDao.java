package com.zeal.dao;

import com.zeal.common.PagedList;
import com.zeal.entity.Album;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
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
     * @param userInfoId 用户ID
     * @param published  是否发布
     * @param page       分页页码 从 1 开始
     * @param pageSize   分页每页数量
     * @return 分页信息
     */
    public PagedList<Album> pagedByUserInfoIdAndPublishStatus(long userInfoId, boolean published, int page, int pageSize) {
        TypedQuery<Album> query = this.entityManager().createQuery("SELECT o FROM Album o where o.userInfo.id = :userInfoId and o.isPublished = :isPublished ORDER BY o.createDate desc ", Album.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter("userInfoId", userInfoId);
        query.setParameter("isPublished", published);
        PagedList<Album> pagedList = new PagedList<>();
        pagedList.setList(query.getResultList());
        pagedList.setSize(pageSize);
        pagedList.setPage(page);
        pagedList.setTotalSize(countByUserInfoIdAndPublishStatus(userInfoId, published));
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
     * @param page      开始页码 从 1 开始
     * @param pageSize  每页数量
     * @param published 是否发布
     * @param tagId     归属的tag id
     * @return
     */
    public PagedList<Album> pagedByPublishStatus(int page, int pageSize, long tagId, boolean published) {
        TypedQuery<Album> query = this.entityManager().createQuery("SELECT o FROM Album o inner join o.albumTags t where o.isPublished = :isPublished and t.id = :tagId ORDER BY o.publishDate desc ", Album.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter("isPublished", published);
        query.setParameter("tagId", tagId);
        PagedList<Album> pagedList = new PagedList<>();
        pagedList.setList(query.getResultList());
        pagedList.setSize(pageSize);
        pagedList.setPage(page);
        pagedList.setTotalSize(countByPublishStatus(published, tagId));
        return pagedList;
    }

    public long countByPublishStatus(boolean published, long tagId) {
        TypedQuery<Long> query = this.entityManager().createQuery("SELECT count(o) FROM Album o inner join o.albumTags t where o.isPublished = :isPublished and t.id = :tagId ORDER BY o.publishDate desc ", Long.class);
        query.setParameter("isPublished", published);
        query.setParameter("tagId", tagId);
        return query.getSingleResult();
    }


    public PagedList<Album> pagedByUserInfoIdAndKeywordLike(long userInfoId, int page, int pageSize, String keyword) {
        TypedQuery<Album> query = this.entityManager().createQuery("SELECT o FROM Album o where o.userInfo.id = :userInfoId and (o.name like :keyword or o.description like :keyword) ORDER BY o.createDate desc ", Album.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter("userInfoId", userInfoId);
        query.setParameter("keyword", "%" + keyword + "%");
        PagedList<Album> pagedList = new PagedList<>();
        pagedList.setList(query.getResultList());
        pagedList.setSize(pageSize);
        pagedList.setPage(page);
        pagedList.setTotalSize(countByUserInfoIdAndKeywordLike(userInfoId, keyword));
        return pagedList;

    }

    private long countByUserInfoIdAndKeywordLike(long userInfoId, String keyword) {
        TypedQuery<Long> query = this.entityManager().createQuery("SELECT count(o) FROM Album o where o.userInfo.id = :userInfoId and (o.name like :keyword or o.description like :keyword) ORDER BY o.createDate desc ", Long.class);
        query.setParameter("userInfoId", userInfoId);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getSingleResult();
    }
}
