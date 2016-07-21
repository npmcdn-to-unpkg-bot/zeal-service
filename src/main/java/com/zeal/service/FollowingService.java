package com.zeal.service;

/**
 * Created by yang_shoulai on 2016/7/21.
 */
public interface FollowingService {


    /**
     * 创建一条关注记录
     *
     * @param follower 关注者ID
     * @param followed 被关注者ID
     */
    void create(long follower, long followed);


    /**
     * 删除一条关注记录
     *
     * @param follower 关注者ID
     * @param followed 被关注者ID
     */
    void delete(long follower, long followed);

    /**
     * 判断关注记录是否存在
     *
     * @param follower 关注者ID
     * @param followed 被关注者ID
     * @return
     */
    boolean hasFollowed(long follower, long followed);

}
