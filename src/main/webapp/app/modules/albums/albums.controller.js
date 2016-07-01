/**
 * Created by yang_shoulai on 2016/6/25.
 */

(function () {
    angular.module("app").controller('AlbumController', ['$scope', 'HttpService', '$log', '$state', '$uibModal', 'AlbumService',
        function ($scope, HttpService, $log, $state, $uibModal, AlbumService) {

            $scope.albums = [];
            $scope.rowSize = 4;
            $scope.row = 10;
            $scope.pageSize = $scope.rowSize * $scope.row;
            $scope.page = 1;
            $scope.totalSize = 0;

            $scope.getAlbums = function () {
                AlbumService.getPublishedAlbums($scope.page, $scope.pageSize).success(function (data) {
                    $scope.albums = [];
                    $scope.page = data.page;
                    $scope.totalSize = data.totalSize;
                    $scope.pageSize = data.size;
                    var albums = data.list;
                    if (albums && albums.length > 0) {
                        for (var i = 0; i < albums.length; i++) {
                            if (i % $scope.rowSize == 0) {
                                $scope.albums.push([]);
                            }
                            var album = albums[i];
                            $scope.albums[(i - i % $scope.rowSize) / $scope.rowSize].push(
                                {
                                    id: album.id,
                                    name: album.name,
                                    size: album.pictures.length,
                                    pictures: album.pictures,
                                    url: album.pictures[0].url,
                                    createDate: album.createDate,
                                    userInfo: album.userInfo
                                }
                            );
                        }
                    }
                }).error(function (data) {
                    $log.info(data.message);
                });
            };

            $scope.onPageChanged = function () {
                $log.info("page = " + $scope.page);
                $scope.getAlbums();
            };
            $scope.getAlbums();

            $scope.onClickAlbum = function (album) {
                //$state.go('pictures', {album: album});
                AlbumService.showAlbumModal(album);
            };
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
})();
