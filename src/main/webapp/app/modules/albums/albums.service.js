/**
 * Created by Administrator on 7/1/2016.
 */
(function () {
    angular.module("app").service("AlbumService", function (HttpService, $uibModal, MessageService) {

        this.getPublishedAlbums = function (page, pageSize, tag) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/album/published?page=" + page + "&pageSize=" + pageSize + "&tag=" + tag
            });
        };

        this.getMyAlbums = function (page, pageSize, keyword, state) {
            keyword = escape(encodeURI(keyword));
            return HttpService.http({
                method: "GET",
                url: "/zeal/my/albums?page=" + page + "&pageSize=" + pageSize + "&keyword=" + keyword + "&state=" + state
            });
        };

        this.getMyAlbumById = function (id) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/album/" + id
            });
        };

        this.showAlbumModal = function (album) {
            if (!album.pictures || album.pictures.length <= 0) {
                MessageService.info({message: "相册为空", size: "sm"});
            } else {
                $uibModal.open({
                    templateUrl: '/zeal/app/modules/albums/pictures.html',
                    size: 'lg',
                    controller: 'AlbumDisplayController',
                    resolve: {
                        album: function () {
                            return album;
                        }
                    },
                    windowClass: "modal-album-display"
                });
            }
        };

        this.showAlbumListModal = function (album) {
            if (!album.pictures || album.pictures.length <= 0) {
                MessageService.info({message: "相册为空", size: "sm"});
            } else {
                $uibModal.open({
                    templateUrl: '/zeal/app/modules/albums/pictures-list.html',
                    size: 'lg',
                    controller: 'AlbumListDisplayController',
                    resolve: {
                        album: function () {
                            return album;
                        }
                    },
                    windowClass: "modal-album-display"
                });
            }
        };

        this.delete = function (album) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/album/delete/" + album.id
            });
        };

        this.publish = function (album) {
            return HttpService.http({
                method: "POST",
                url: "/zeal/album/publish/" + album.id
            });
        };

        this.unpublish = function (album) {
            return HttpService.http({
                method: "POST",
                url: "/zeal/album/unpublish/" + album.id
            });
        };

        this.showPicture = function (picture) {
            $uibModal.open({
                templateUrl: '/zeal/app/modules/albums/picture.html',
                size: 'md',
                controller: 'PictureDisplayController',
                resolve: {
                    picture: picture
                }
            });
        };

        this.getChildrenTagsByTagId = function (tagId) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/albumTag/children?id=" + tagId
            });
        };

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
