package com.zeal.activiti.custom;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 7/21/2016.
 */
@Service
public class CustomGroupEntityManager extends GroupEntityManager {

    @Override
    public List<Group> findGroupsByUser(final String userCode) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        throw new RuntimeException("not implement method.");
    }


}
