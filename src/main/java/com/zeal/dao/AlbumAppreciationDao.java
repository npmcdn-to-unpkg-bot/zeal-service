package com.zeal.dao;

import com.zeal.entity.AlbumAppreciation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/16.
 */
@Repository
public class AlbumAppreciationDao extends AbstractBaseDao<AlbumAppreciation> {

    /**
     * 根据点赞者ID和相册ID获取点赞记录信息
     *
     * @param userInfoId 点赞者ID
     * @param albumId    相册ID
     * @return
     */
    public List<AlbumAppreciation> findByUserInfoIdAndAlbumId(long userInfoId, long albumId) {
        String sql = "select o from AlbumAppreciation o where o.userInfo.id = :userInfoId and o.album.id = :albumId order by o.time desc";
        TypedQuery<AlbumAppreciation> query = this.entityManager().createQuery(sql, AlbumAppreciation.class);
        query.setParameter("userInfoId", userInfoId);
        query.setParameter("albumId", albumId);
        return query.getResultList();
    }

    /**
     * 获取用户的所有点赞记录
     *
     * @param userInfoId 用户ID
     * @return
     */
    public List<AlbumAppreciation> findByUserInfoId(long userInfoId) {
        String sql = "select o from AlbumAppreciation o where o.userInfo.id = :userInfoId order by o.time desc";
        TypedQuery<AlbumAppreciation> query = this.entityManager().createQuery(sql, AlbumAppreciation.class);
        query.setParameter("userInfoId", userInfoId);
        return query.getResultList();
    }

    /**
     * 获取相册的所有点赞记录
     *
     * @param albumId 相册ID
     * @return
     */
    public List<AlbumAppreciation> findByAlbumId(long albumId) {
        String sql = "select o from AlbumAppreciation o where o.album.id = :albumId order by o.time desc";
        TypedQuery<AlbumAppreciation> query = this.entityManager().createQuery(sql, AlbumAppreciation.class);
        query.setParameter("albumId", albumId);
        return query.getResultList();
    }

    /**
     * 根据用户ID和相册ID删除点赞记录
     *
     * @param userInfoId 点赞者ID
     * @param albumId    相册ID
     */
    @Transactional
    public void deleteByUserInfoAndAlbumId(long userInfoId, long albumId) {
        String sql = "delete from AlbumAppreciation o where o.album.id = :albumId and o.userInfo.id = :userInfoId";
        Query query = this.entityManager().createQuery(sql);
        query.setParameter("userInfoId", userInfoId);
        query.setParameter("albumId", albumId);
        query.executeUpdate();
    }

}
