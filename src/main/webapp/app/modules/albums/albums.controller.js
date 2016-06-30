/**
 * Created by yang_shoulai on 2016/6/25.
 */

(function () {
    angular.module("app").controller('AlbumController', ['$scope', 'HttpService', '$log', '$state', '$uibModal',
        function ($scope, HttpService, $log, $state, $uibModal) {

            $scope.albums = [];
            $scope.rowSize = 4;
            $scope.row = 10;
            $scope.pageSize = $scope.rowSize * $scope.row;
            $scope.page = 1;
            $scope.totalSize = 0;

            $scope.getAlbums = function () {
                HttpService.http({
                    method: "GET",
                    url: "/zeal/album/published?page=" + $scope.page + "&pageSize=" + $scope.pageSize
                }).success(function (data) {
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
        }]);


    angular.module("app").controller('AlbumDisplayController', ['$scope', 'HttpService', '$log', '$stateParams', 'CookieService', 'album',
        function ($scope, HttpService, $log, $stateParams, CookieService, album) {
            $scope.album = album;
            $scope.slides = album.pictures;
            $scope.carouselMode = true;
            $scope.active = 0;
            $scope.interval = 2000;
            $scope.noWrapMode = false;
            /*if ($stateParams.album) {
             CookieService.put(CookieService.KEY_CONSTANT.album_last_visited, $stateParams.album);
             $scope.album = $stateParams.album;
             } else {
             $scope.album = CookieService.get(CookieService.KEY_CONSTANT.album_last_visited);
             }*/
        }]);
})();
