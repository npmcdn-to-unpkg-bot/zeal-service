package com.zeal.dao;

import com.zeal.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public T find(Long id) {
        return entityManager().find(entityClass, id);
    }


    @Override
    @Transactional
    public void save(T t) {
        entityManager().persist(t);
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
        if (this.entityManager().contains(this)) {
            this.entityManager().remove(this);
        } else {
            T attached = find(t.getId());
            this.entityManager().remove(attached);
        }
    }

    @Override
    public List<T> findAll() {
        return this.entityManager().createQuery("SELECT o FROM " + entityClass.getSimpleName() + " o", entityClass).getResultList();
    }

    @Override
    public List<T> pagedList(int firstResult, int maxResults) {
        return this.entityManager().createQuery("SELECT o FROM " + entityClass.getSimpleName() + " o", entityClass).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
