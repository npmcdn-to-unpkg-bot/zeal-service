package com.zeal.dao;

import com.zeal.entity.AlbumCollection;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Administrator on 7/14/2016.
 */
@Repository
public class AlbumCollectionDao extends AbstractBaseDao<AlbumCollection> {


    /**
     * 获取用户所有收藏的相册记录
     *
     * @param userId 用户ID
     * @return
     */
    public List<AlbumCollection> findByUserInfoIdEquals(long userId) {
        String sql = "select o from AlbumCollection o where o.userInfo.id = :userId";
        TypedQuery<AlbumCollection> query = this.entityManager().createQuery(sql, AlbumCollection.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    /**
     * 获取用户所有收藏的相册记录数量
     *
     * @param userId 用户ID
     * @return
     */
    public long countByUserInfoIdEquals(long userId) {
        String sql = "select count(o) from AlbumCollection o where o.userInfo.id = :userId";
        TypedQuery<Long> query = this.entityManager().createQuery(sql, Long.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }


    /**
     * 获取相册的所有收藏
     *
     * @param albumId 相册ID
     * @return
     */
    public List<AlbumCollection> findByAlbumIdEquals(long albumId) {
        String sql = "select o from AlbumCollection o where o.album.id = :albumId";
        TypedQuery<AlbumCollection> query = this.entityManager().createQuery(sql, AlbumCollection.class);
        query.setParameter("albumId", albumId);
        return query.getResultList();
    }

    /**
     * 获取相册所有收藏的数量
     *
     * @param albumId 相册ID
     * @return
     */
    public long countByAlbumIdEquals(long albumId) {
        String sql = "select count(o) from AlbumCollection o where o.album.id = :albumId";
        TypedQuery<Long> query = this.entityManager().createQuery(sql, Long.class);
        query.setParameter("albumId", albumId);
        return query.getSingleResult();
    }


    /**
     * 根据用户ID和相册ID删除记录
     *
     * @param userId  用户ID
     * @param albumId 相册ID
     */
    @Transactional
    public void delete(long userId, long albumId) {
        String sql = "delete from AlbumCollection o where o.album.id = :albumId and o.userInfo.id = :userInfoId";
        Query query = this.entityManager().createQuery(sql);
        query.setParameter("albumId", albumId);
        query.setParameter("userInfoId", userId);
        query.executeUpdate();
    }


    /**
     * 根据用户ID和相册ID获取收藏记录
     *
     * @param userId  用户ID
     * @param albumId 相册ID
     * @return
     */
    public List<AlbumCollection> findByUserInfoIdAndAlbumId(long userId, long albumId) {
        String sql = "select o from AlbumCollection o where o.album.id = :albumId and o.userInfo.id = :userInfoId";
        TypedQuery<AlbumCollection> query = this.entityManager().createQuery(sql, AlbumCollection.class);
        query.setParameter("albumId", albumId);
        query.setParameter("userInfoId", userId);
        return query.getResultList();
    }

}
