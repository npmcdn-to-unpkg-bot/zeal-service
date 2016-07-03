/**
 * Created by Administrator on 7/1/2016.
 */
(function () {
    angular.module("app").service("AlbumService", function (HttpService, $uibModal, MessageService) {

        this.getPublishedAlbums = function (page, pageSize) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/album/published?page=" + page + "&pageSize=" + pageSize
            });
        };

        this.getMyAlbums = function (page, pageSize, keyword) {
            keyword = escape(encodeURI(keyword));
            return HttpService.http({
                method: "GET",
                url: "/zeal/my/albums?page=" + page + "&pageSize=" + pageSize + "&keyword=" + keyword
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

    });
})();
