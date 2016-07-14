package com.zeal.service;

import com.zeal.exception.NoAuthorityException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 7/14/2016.
 */
public interface AuthorityCheckService {


    /**
     * 检验当前用户是否有指定相册的读取权限
     *
     * @param currentUserId 当前登录用户ID
     * @param albumId       指定相册ID
     * @throws NoAuthorityException 如果没有权限，则抛出该异常
     */
    void checkAlbumReadAuthority(long currentUserId, long albumId) throws NoAuthorityException;


    /**
     * 检验当前用户是否有指定相册的读取权限
     *
     * @param request http request 可以获取当前登录用户的ID
     * @param albumId 指定相册ID
     * @throws NoAuthorityException 如果没有权限，则抛出该异常
     */
    void checkAlbumReadAuthority(HttpServletRequest request, long albumId) throws NoAuthorityException;


    /**
     * 检验当前用户是否有指定相册的修改权限
     *
     * @param currentUserId 当前登录用户ID
     * @param albumId       指定相册ID
     * @throws NoAuthorityException 如果没有权限，则抛出该异常
     */
    void checkAlbumModifyAuthority(long currentUserId, long albumId) throws NoAuthorityException;


    /**
     * 检验当前用户是否有指定相册的读取权限
     *
     * @param request http request 可以获取当前登录用户的ID
     * @param albumId 指定相册ID
     * @throws NoAuthorityException 如果没有权限，则抛出该异常
     */
    void checkAlbumModifyAuthority(HttpServletRequest request, long albumId) throws NoAuthorityException;


    /**
     * 检验当前用户是否有指定相片的读取权限
     *
     * @param currentUserId 当前登录用户ID
     * @param pictureId       指定相片ID
     * @throws NoAuthorityException 如果没有权限，则抛出该异常
     */
    void checkPictureReadAuthority(long currentUserId, long pictureId) throws NoAuthorityException;


    /**
     * 检验当前用户是否有指定相片的读取权限
     *
     * @param request http request 可以获取当前登录用户的ID
     * @param pictureId 指定相片ID
     * @throws NoAuthorityException 如果没有权限，则抛出该异常
     */
    void checkPictureReadAuthority(HttpServletRequest request, long pictureId) throws NoAuthorityException;


    /**
     * 检验当前用户是否有指定相片的修改权限
     *
     * @param currentUserId 当前登录用户ID
     * @param pictureId       指定相片ID
     * @throws NoAuthorityException 如果没有权限，则抛出该异常
     */
    void checkPictureModifyAuthority(long currentUserId, long pictureId) throws NoAuthorityException;


    /**
     * 检验当前用户是否有指定相片的读取权限
     *
     * @param request   http request 可以获取当前登录用户的ID
     * @param pictureId 相片ID
     * @throws NoAuthorityException 如果没有权限，则抛出该异常
     */
    void checkPictureModifyAuthority(HttpServletRequest request, long pictureId) throws NoAuthorityException;


}
