package com.zeal.dao;

import com.zeal.entity.AlbumAuthorAppreciation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/16.
 */
@Repository
public class AlbumAuthorAppreciationDao extends AbstractBaseDao<AlbumAuthorAppreciation> {


    public List<AlbumAuthorAppreciation> find(long appreciator, long appreciated) {
        String sql = "select o from AlbumAuthorAppreciation o where o.appreciator.id = :appreciator and o.appreciated.id = :appreciated";
        TypedQuery<AlbumAuthorAppreciation> query = this.entityManager().createQuery(sql, AlbumAuthorAppreciation.class);
        query.setParameter("appreciator", appreciator);
        query.setParameter("appreciated", appreciated);
        return query.getResultList();
    }


    /**
     * 获取用户的点赞记录
     *
     * @param appreciator
     * @return
     */
    public List<AlbumAuthorAppreciation> findAppreciations(long appreciator) {
        String sql = "select o from AlbumAuthorAppreciation o where o.appreciator.id = :appreciator";
        TypedQuery<AlbumAuthorAppreciation> query = this.entityManager().createQuery(sql, AlbumAuthorAppreciation.class);
        query.setParameter("appreciator", appreciator);
        return query.getResultList();
    }

    /**
     * 获取用户的被赞记录
     *
     * @param appreciated
     * @return
     */
    public List<AlbumAuthorAppreciation> findAppreciatedRecords(long appreciated) {
        String sql = "select o from AlbumAuthorAppreciation o where o.appreciated.id = :appreciated";
        TypedQuery<AlbumAuthorAppreciation> query = this.entityManager().createQuery(sql, AlbumAuthorAppreciation.class);
        query.setParameter("appreciated", appreciated);
        return query.getResultList();
    }

    /**
     * 删除被赞记录
     *
     * @param appreciator
     * @param appreciated
     */
    @Transactional
    public void delete(long appreciator, long appreciated) {
        String sql = "delete from AlbumAuthorAppreciation o where o.appreciator.id = :appreciator and o.appreciated.id = :appreciated";
        Query query = this.entityManager().createQuery(sql);
        query.setParameter("appreciator", appreciator);
        query.setParameter("appreciated", appreciated);
        query.executeUpdate();
    }

}
