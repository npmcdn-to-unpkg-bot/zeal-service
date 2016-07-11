/**
 * Created by yang_shoulai on 2016/6/25.
 */

(function () {
    angular.module("app").controller('AlbumController', ['$scope', 'HttpService', '$log', '$state', '$uibModal', 'AlbumService', '$stateParams',
        function ($scope, HttpService, $log, $state, $uibModal, AlbumService, $stateParams) {
            var parentTag = $stateParams.tag;
            $scope.pagination = {
                page: 1,
                totalSize: 0,
                pageSize: 20
            };
            $scope.childTags = [];
            $scope.childTagId = -1;
            $scope.targetAlbums = [];

            $scope.getAlbums = function () {
                AlbumService.getPublishedAlbums($scope.pagination.page, $scope.pagination.pageSize, $scope.childTagId).success(function (data) {
                    $scope.targetAlbums = [];
                    $scope.pagination.page = data.page;
                    $scope.pagination.totalSize = data.totalSize;
                    var albums = data.list;
                    if (albums && albums.length > 0) {
                        for (var i = 0; i < albums.length; i++) {
                            var album = albums[i];
                            $scope.targetAlbums.push(
                                {
                                    id: album.id,
                                    name: album.name,
                                    size: album.pictures.length,
                                    pictures: album.pictures,
                                    url: "/zeal/album/thumbnail/" + album.id,
                                    createDate: album.createDate,
                                    userInfo: album.userInfo,
                                    publishDate: album.publishDate,
                                    tags: album.tags
                                }
                            );
                        }
                    }
                }).error(function (data) {
                    $log.info(data.message);
                });
            };

            $scope.onPageChanged = function () {
                $log.info("page = " + $scope.pagination.page);
                $scope.getAlbums();
            };

            $scope.onClickAlbum = function (album) {
                //$state.go('pictures', {album: album});
                AlbumService.showAlbumModal(album);
            };

            $scope.childTagClick = function (childTag) {
                $scope.childTagId = childTag.id;
                $scope.pagination.page = 1;
                $scope.pagination.totalSize = 0;
                $scope.targetAlbums = [];
                $scope.getAlbums();
            };

            AlbumService.getChildrenTagsByTagId(parentTag).success(function (data) {
                $scope.childTags = data;
                if (data && data.length > 0) {
                    $scope.childTagId = data[0].id;
                }
                $scope.getAlbums();
            });
        }]);


    angular.module("app").controller('AlbumDisplayController', ['$scope', 'HttpService', '$log', '$stateParams', 'CookieService', 'album',
        function ($scope, HttpService, $log, $stateParams, CookieService, album) {
            $scope.album = album;
            $scope.slides = album.pictures;
            $scope.carouselMode = true;
            $scope.active = 0;
            $scope.interval = 2000;
            $scope.noWrapMode = false;
        }]);

    angular.module("app").controller('AlbumListDisplayController', ['$scope', 'HttpService', '$log', '$stateParams', 'CookieService', 'album',
        function ($scope, HttpService, $log, $stateParams, CookieService, album) {
            $scope.album = album;
        }]);

    angular.module("app").controller('PictureDisplayController', function ($scope, picture) {
        $scope.picture = picture;
    });
})();
