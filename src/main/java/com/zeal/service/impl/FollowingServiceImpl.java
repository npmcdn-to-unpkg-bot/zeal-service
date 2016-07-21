package com.zeal.service.impl;

import com.zeal.dao.FollowingDao;
import com.zeal.exception.BizException;
import com.zeal.service.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yang_shoulai on 2016/7/21.
 */
@Service
public class FollowingServiceImpl implements FollowingService {

    @Autowired
    private FollowingDao followingDao;

    /**
     * 创建一条关注记录
     *
     * @param follower 关注者ID
     * @param followed 被关注者ID
     */
    @Transactional
    @Override
    public void create(long follower, long followed) {
        if (follower == followed) {
            throw new BizException("自己不能关注自己");
        }
        followingDao.create(follower, followed);
    }

    /**
     * 删除一条关注记录
     *
     * @param follower 关注者ID
     * @param followed 被关注者ID
     */
    @Transactional
    @Override
    public void delete(long follower, long followed) {
        followingDao.delete(follower, followed);
    }

    /**
     * 判断关注记录是否存在
     *
     * @param follower 关注者ID
     * @param followed 被关注者ID
     * @return
     */
    @Override
    public boolean hasFollowed(long follower, long followed) {

        return followingDao.exist(follower, followed);
    }
}
