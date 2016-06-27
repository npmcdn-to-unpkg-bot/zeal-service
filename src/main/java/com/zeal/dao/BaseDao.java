package com.zeal.dao;

import com.zeal.entity.BaseEntity;

import java.util.List;

/**
 * Created by yang_shoulai on 2016/6/27.
 */
public interface BaseDao<T extends BaseEntity> {

    /**
     * 根据主键获取Entity对象信息
     *
     * @param id 主键ID
     * @return
     */
    T find(Long id);

    /**
     * 保存实体
     *
     * @param t
     */
    void save(T t);

    /**
     * 更新实体
     *
     * @param t
     * @return
     */
    T update(T t);

    /**
     * 删除实体
     *
     * @param t
     */
    void delete(T t);

    /**
     * 获取所有实体
     *
     * @param t
     */
    List<T> findAll();

    /**
     * 分页查询实体
     *
     * @param firstResult 开始位置
     * @param maxResults  数量
     * @return
     */
    List<T> pagedList(int firstResult, int maxResults);
}
