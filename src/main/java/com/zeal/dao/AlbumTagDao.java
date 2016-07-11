package com.zeal.dao;

import com.zeal.entity.AlbumTag;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by yang_shoulai on 2016/7/8.
 */
@Repository
public class AlbumTagDao extends AbstractBaseDao<AlbumTag> {

    /**
     * 根据父tag id获取该tag下的所有子tag
     *
     * @param id
     * @return
     */
    public List<AlbumTag> findChildrenById(long id) {
        TypedQuery<AlbumTag> query = this.entityManager().createQuery("select o from AlbumTag o where o.parent = :parent", AlbumTag.class);
        query.setParameter("parent", id);
        return query.getResultList();
    }


    public List<AlbumTag> findAllChildren() {
        TypedQuery<AlbumTag> query = this.entityManager().createQuery("select o from AlbumTag o where o.parent != 0", AlbumTag.class);
        return query.getResultList();
    }
}
