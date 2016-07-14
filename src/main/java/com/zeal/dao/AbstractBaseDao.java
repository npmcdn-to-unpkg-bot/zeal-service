package com.zeal.dao;

import com.zeal.common.PagedList;
import com.zeal.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 6/27/2016.
 */
public abstract class AbstractBaseDao<T extends BaseEntity> implements BaseDao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager entityManager() {
        return this.entityManager;
    }

    private Class<T> entityClass;

    public AbstractBaseDao() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class<T>) params[0];
    }

    @Override
    public T find(long id) {
        return entityManager().find(entityClass, id);
    }


    @Override
    @Transactional
    public void insert(T t) {
        entityManager().persist(t);
        entityManager().flush();
    }

    @Override
    @Transactional
    public void batchInsert(List<T> entities) {
        if (entities != null && !entities.isEmpty()) {
            for (int i = 0; i < entities.size(); i++) {
                entityManager().persist(entities.get(i));
                if (i % 50 == 0) {
                    entityManager().flush();
                    entityManager().clear();
                }
            }
        }
    }

    @Override
    @Transactional
    public T update(T t) {
        T merged = this.entityManager().merge(t);
        this.entityManager().flush();
        return merged;
    }

    @Override
    @Transactional
    public void delete(T t) {
        if (this.entityManager().contains(t)) {
            this.entityManager().remove(t);
        } else {
            T attached = find(t.getId());
            this.entityManager().remove(attached);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        Query query = this.entityManager.createQuery("DELETE FROM " + entityClass.getSimpleName() + " o where o.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<T> findAll() {
        return this.entityManager().createQuery("SELECT o FROM " + entityClass.getSimpleName() + " o", entityClass).getResultList();
    }

    @Override
    public PagedList<T> paged(int page, int size) {
        List<T> list = this.entityManager().createQuery("SELECT o FROM " + entityClass.getSimpleName() + " o", entityClass).setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
        PagedList<T> pagedList = new PagedList<>();
        pagedList.setList(list);
        pagedList.setTotalSize(countAll());
        pagedList.setPage(page);
        pagedList.setSize(size);
        return pagedList;
    }


    @Override
    public long countAll() {
        return this.entityManager().createQuery("SELECT count(o) FROM " + entityClass.getSimpleName() + " o", Long.class).getSingleResult();
    }
}
