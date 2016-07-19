/**
 * Created by Administrator on 7/1/2016.
 */
(function () {
    angular.module("zeal-common").service("AlbumService", function (HttpService, $uibModal, MessageService) {
        /**
         * 分页获取相册列表信息
         * @param page 页码
         * @param pageSize 每页数量
         * @param tag 相册tag
         * @returns {*|{promise, success, error}}
         */
        this.getPublishedAlbums = function (page, pageSize, tag) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/album/published?page=" + page + "&pageSize=" + pageSize + "&tag=" + tag
            });
        };

        /**
         * 分页获取我的相册信息
         * @param page 页码
         * @param pageSize 每页数量
         * @param keyword 查询关键字
         * @param state 发布状态， -1 -> 全部；0 -> 未发布；1 -> 已发布
         * @returns {*|{promise, success, error}}
         */
        this.getMyAlbums = function (page, pageSize, keyword, state) {
            keyword = escape(encodeURI(keyword));
            return HttpService.http({
                method: "GET",
                url: "/zeal/my/albums?page=" + page + "&pageSize=" + pageSize + "&keyword=" + keyword + "&state=" + state
            });
        };

        /**
         * 根据相册ID获取相册信息
         * @param id
         * @returns {*|{promise, success, error}}
         */
        this.getAlbumById = function (id) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/album/" + id
            });
        };


        /**
         * 删除相册
         * @param album
         * @returns {*|{promise, success, error}}
         */
        this.delete = function (album) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/album/delete/" + album.id
            });
        };

        /**
         * 发布相册
         * @param album
         * @returns {*|{promise, success, error}}
         */
        this.publish = function (album) {
            return HttpService.http({
                method: "POST",
                url: "/zeal/album/publish/" + album.id
            });
        };

        /**
         * 取消发布相册
         * @param album
         * @returns {*|{promise, success, error}}
         */
        this.unpublish = function (album) {
            return HttpService.http({
                method: "POST",
                url: "/zeal/album/unpublish/" + album.id
            });
        };

        /**
         * 获取子tag信息
         * @param tagId 父tag ID
         * @returns {*|{promise, success, error}}
         */
        this.getChildrenTagsByTagId = function (tagId) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/albumTag/children?id=" + tagId
            });
        };

        /**
         * 获取数据库中所有子tag信息
         * @returns {*|{promise, success, error}}
         */
        this.getAllChildrenTags = function () {
            return HttpService.http({
                method: "GET",
                url: "/zeal/albumTag/all"
            });
        };

        /**
         * 收藏相册
         * @param albumId
         * @returns {*|{promise, success, error}}
         */
        this.collect = function (albumId) {
            return HttpService.http({
                method: "GET",
                url: "/zeal//collection/album/add?albumId=" + albumId
            });
        };

        /**
         * 取消收藏相册
         * @param albumId
         * @returns {*|{promise, success, error}}
         */
        this.uncollect = function (albumId) {
            return HttpService.http({
                method: "GET",
                url: "/zeal//collection/album/cancel?albumId=" + albumId
            });
        };

        /**
         * 点赞相册
         * @param albumId
         * @returns {{promise, success, error}|*}
         */
        this.appreciate = function (albumId) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/appreciation/album/add?albumId=" + albumId
            });
        };

        /**
         * 取消点暂
         * @param albumId
         * @returns {{promise, success, error}|*}
         */
        this.unAppreciate = function (albumId) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/appreciation/album/cancel?albumId=" + albumId
            });
        };

        /**
         * 点赞作者
         * @param authorId 作者ID
         */
        this.appreciateAuthor = function (authorId) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/appreciation/author/add?appreciated=" + authorId
            });
        };

        /**
         * 取消点赞作者
         * @param authorId 作者ID
         */
        this.unAppreciateAuthor = function (authorId) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/appreciation/author/cancel?appreciated=" + authorId
            });
        };
    });
})();
