package com.zeal.common;

import java.util.List;

/**
 * Created by Administrator on 6/28/2016.
 */
public class PagedList<T> {

    /**
     * 数据集
     */
    private List<T> list;

    /**
     * 总共数据集的数量
     */
    private long totalSize;

    /**
     * 当前分页的页码
     */
    private int page;

    /**
     * 每页的数量
     */
    private int size;


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
