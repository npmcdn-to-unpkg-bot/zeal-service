package com.zeal.dao;

import com.zeal.entity.Following;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;

/**
 * Created by yang_shoulai on 2016/7/21.
 */
@Repository
public class FollowingDao extends AbstractBaseDao<Following> {

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 创建一条关注记录
     *
     * @param followerId 关注者ID
     * @param followedId 被关注者ID
     */
    @Transactional
    public void create(long followerId, long followedId) {
        if (!exist(followerId, followedId)) {
            Following following = new Following();
            following.setFollowed(userInfoDao.find(followedId));
            following.setFollower(userInfoDao.find(followerId));
            following.setTime(new Date());
            insert(following);
        }
    }


    /**
     * 判断关注记录是否存在
     *
     * @param followerId 关注者ID
     * @param followedId 被关注者ID
     * @return
     */
    public boolean exist(long followerId, long followedId) {
        TypedQuery<Following> query = this.entityManager().createQuery("select o from Following o where o.followed.id = :followedId and o.follower.id = :followerId", Following.class);
        query.setParameter("followedId", followedId);
        query.setParameter("followerId", followerId);
        return !query.getResultList().isEmpty();
    }

    /**
     * 删除一条关注记录
     *
     * @param followerId 关注者ID
     * @param followedId 被关注者ID
     */
    @Transactional
    public void delete(long followerId, long followedId) {
        Query query = this.entityManager().createQuery("delete from Following o where o.followed.id = :followedId and o.follower.id = :followerId");
        query.setParameter("followedId", followedId);
        query.setParameter("followerId", followerId);
        query.executeUpdate();
    }


}
