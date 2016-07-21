package com.zeal.dao;

import com.zeal.entity.UserInfo;
import com.zeal.http.response.album.AlbumAuthorInfo;
import com.zeal.utils.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Administrator on 6/27/2016.
 */
@Repository
public class UserInfoDao extends AbstractBaseDao<UserInfo> {

    /**
     * 根据用户登录名和密码查找用户信息
     *
     * @param loginName 用户登录名
     * @param password  用户密码（未加密）
     * @return 用户信息
     */
    public UserInfo findByLoginNameAndPasswordEquals(String loginName, String password) {
        String sql = "SELECT o FROM UserInfo o WHERE (o.email = :loginName or o.phoneNumber = :loginName) and o.password = :password";
        TypedQuery<UserInfo> query = this.entityManager().createQuery(sql, UserInfo.class);
        query.setParameter("loginName", loginName);
        query.setParameter("password", password);
        List<UserInfo> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 根据手机号查找用户信息
     *
     * @param phoneNumber 用户手机号
     * @return 用户信息列表
     */
    public List<UserInfo> findByPhoneNumberEquals(String phoneNumber) {
        String sql = "SELECT o FROM UserInfo o WHERE o.phoneNumber = :phoneNumber";
        TypedQuery<UserInfo> query = this.entityManager().createQuery(sql, UserInfo.class);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getResultList();
    }


    /**
     * 根据邮箱查找用户信息
     *
     * @param email 用户邮箱
     * @return 用户信息列表
     */
    public List<UserInfo> findByEmailEquals(String email) {
        String sql = "SELECT o FROM UserInfo o WHERE o.email = :email";
        TypedQuery<UserInfo> query = this.entityManager().createQuery(sql, UserInfo.class);
        query.setParameter("email", email);
        return query.getResultList();
    }

    /**
     * 查询邮箱是否已经存在
     *
     * @param email
     * @return
     */
    public boolean emailExist(String email) {
        String sql = "select count(o) from UserInfo o where o.email = :email";
        TypedQuery<Long> query = this.entityManager().createQuery(sql, Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0L;
    }


    /**
     * 获取相册作者信息
     *
     * @param id            作者ID
     * @param currentUserId 当前登录用户ID
     * @return
     */
    public AlbumAuthorInfo author(long id, long currentUserId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT u.id, u.nick_name,")
                .append("(select count(1) from t_album_author_appreciation where appreciated = u.id) as appreciationCount,")
                .append("(select count(1) from t_album where published = 1 and user_info = u.id) as publishedCount,")
                .append("(select count(1) from t_album where published = 0 and user_info = u.id) as unpublishedCount,")
                .append("(select count(1) from t_album_collection where user_info = u.id) as collectionCount,")
                .append("(SELECT count(1) from t_album_author_appreciation where appreciator = :appreciator and appreciated = u.id) as appreciated,")
                .append("u.email,")
                .append("u.photo,")
                .append("u.description,")
                .append("(select count(1) from t_following where followed = u.id) as followerCount,")
                .append("(select count(1) from t_following where follower = u.id) as followingCount,")
                .append("(select count(1) from t_following where follower = :follower and followed = u.id) as userFollowCount")
                .append(" FROM t_user_info u where u.id = :id");

        Query query = this.entityManager().createNativeQuery(buffer.toString());
        query.setParameter("appreciator", currentUserId);
        query.setParameter("follower", currentUserId);
        query.setParameter("id", id);
        Object object = query.getSingleResult();
        Object[] array = (Object[]) object;
        AlbumAuthorInfo albumAuthorInfo = new AlbumAuthorInfo();
        albumAuthorInfo.setId(((BigInteger) array[0]).longValue());
        albumAuthorInfo.setNickName((String) array[1]);
        albumAuthorInfo.setAppreciationCount(((BigInteger) array[2]).longValue());
        albumAuthorInfo.setPublishedCount(((BigInteger) array[3]).longValue());
        albumAuthorInfo.setUnpublishedCount(((BigInteger) array[4]).longValue());
        albumAuthorInfo.setCollectionCount(((BigInteger) array[5]).longValue());
        albumAuthorInfo.setAppreciated(((BigInteger) array[6]).longValue() > 0);
        //TODO 以下属性值需要设置
        albumAuthorInfo.setEmail((String) array[7]);
        albumAuthorInfo.setPhoto((String) array[8]);
        albumAuthorInfo.setDescription((String) array[9]);
        albumAuthorInfo.setFollowerCount(((BigInteger) array[10]).longValue());
        albumAuthorInfo.setFollowingCount(((BigInteger) array[11]).longValue());
        albumAuthorInfo.setFollowed(((BigInteger) array[12]).longValue() > 0);
        albumAuthorInfo.setCommentCount(0);
        return albumAuthorInfo;
    }
}
