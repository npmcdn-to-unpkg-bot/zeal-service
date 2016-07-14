/**
 * Created by yang_shoulai on 2016/6/25.
 */

(function () {
    angular.module("app").controller('AlbumController', ['$scope', 'HttpService', '$log', '$state', '$uibModal', 'AlbumService', '$stateParams', '$location',
        function ($scope, HttpService, $log, $state, $uibModal, AlbumService, $stateParams, $location) {
            var parentTag = $stateParams.tag;
            $scope.pagination = {
                page: 1,
                totalSize: 0,
                pageSize: 30
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
                                    url: "/zeal/picture/resize/" + album.pictures[0].id + "?width=480&height=480",
                                    createDate: album.createDate,
                                    userInfo: album.userInfo,
                                    published: album.published,
                                    publishDate: album.publishDate,
                                    tags: album.tags,
                                    userInfo:album.userInfo
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
                $location.path('/albums/album/' + album.id);
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


    angular.module("app").controller('AlbumDisplayController', ['$scope', 'HttpService', '$log', '$stateParams', 'AlbumService', 'albumPromise',
        function ($scope, HttpService, $log, $stateParams, AlbumService, albumPromise) {
            albumPromise.success(function (album) {
                $scope.album = album;
                $scope.slides = album.pictures;
            });
        }]);

    angular.module("app").controller('AlbumListDisplayController', ['$scope', 'HttpService', '$log', '$stateParams', 'CookieService', 'album',
        function ($scope, HttpService, $log, $stateParams, CookieService, album) {
            $scope.album = album;
        }]);

    angular.module("app").controller('PictureDisplayController', function ($scope, picture) {
        $scope.picture = picture;
    });
})();
