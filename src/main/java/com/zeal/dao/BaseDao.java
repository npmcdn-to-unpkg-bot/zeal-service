package com.zeal.dao;

import com.zeal.common.PagedList;
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
    T find(long id);

    /**
     * 保存实体
     *
     * @param t
     */
    void insert(T t);

    /**
     * 批量插入
     *
     * @param entities
     */
    void batchInsert(List<T> entities);

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
     * 删除实体
     *
     * @param t
     */
    void delete(long id);

    /**
     * 获取所有实体
     */
    List<T> findAll();

    /**
     * 分页查询实体
     *
     * @param page 分页的页码 从 1 开始
     * @param size 每页展示的数量
     * @return
     */
    PagedList<T> paged(int page, int size);

    /**
     * 获取所有实体的数量
     *
     * @return
     */
    long countAll();

}
