package com.zeal.dao;

import com.zeal.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 6/27/2016.
 */
public abstract class AbstractBaseDao<T extends BaseEntity> {

    private Class<T> entityClass;

    public AbstractBaseDao() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class<T>) params[0];
    }

    protected abstract EntityManager entityManager();

    public T find(Long id) {
        return entityManager().find(entityClass, id);
    }


    @Transactional
    public void save(T t) {
        entityManager().persist(t);
    }


    @Transactional
    public T update(T t) {
        T merged = this.entityManager().merge(t);
        this.entityManager().flush();
        return t;
    }

    @Transactional
    public void delete(T t) {
        if (this.entityManager().contains(this)) {
            this.entityManager().remove(this);
        } else {
            T attached = find(t.getId());
            this.entityManager().remove(attached);
        }
    }

    public List<T> findAll() {
        return this.entityManager().createQuery("SELECT o FROM " + entityClass.getSimpleName() + " o", entityClass).getResultList();
    }


    public List<T> pagedList(int firstResult, int maxResults) {
        return this.entityManager().createQuery("SELECT o FROM "+entityClass.getSimpleName()+" o", entityClass).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
