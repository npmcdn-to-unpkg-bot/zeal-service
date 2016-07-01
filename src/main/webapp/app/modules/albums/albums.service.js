/**
 * Created by Administrator on 7/1/2016.
 */
(function () {
    angular.module("app").service("AlbumService", function (HttpService, $uibModal) {

        this.getPublishedAlbums = function (page, pageSize) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/album/published?page=" + page + "&pageSize=" + pageSize
            });
        };

        this.getMyAlbums = function (page, pageSize) {
            return HttpService.http({
                method: "GET",
                url: "/zeal/my/albums?page=" + page + "&pageSize=" + pageSize
            });
        };

        this.showAlbumModal = function (album) {
            $uibModal.open({
                templateUrl: 'modules/albums/pictures.html',
                size: 'lg',
                controller: 'AlbumDisplayController',
                resolve: {
                    album: function () {
                        return album;
                    }
                },
                windowClass: "modal-album-display"
            });
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
    });
})();
