/**
 * Created by Administrator on 7/1/2016.
 */
(function () {
    angular.module("app").controller('MyController', function ($scope, $state) {
        $state.go('my.albums');
    });

    angular.module("app").controller('MyAlbumsController', function ($scope, AlbumService, MessageService, $uibModal) {
        $scope.page = 1;
        $scope.pageSize = 10;
        $scope.albums = [];
        $scope.totalSize = 0;
        var pagedAlbums = function () {
            AlbumService.getMyAlbums($scope.page, $scope.pageSize)
                .success(function (data) {
                    $scope.albums = data.list;
                    $scope.page = data.page;
                    $scope.pageSize = data.size;
                    $scope.totalSize = data.totalSize;
                });
        };
        var removeAlbum = function (id) {
            if ($scope.albums.length > 0) {
                for (var index = 0; index < $scope.albums.length; index++) {
                    if ($scope.albums[index].id == id) {
                        $scope.albums.splice(index, 1);
                        return;
                    }
                }
            }
        };
        pagedAlbums();
        $scope.onAlbumClick = function (album) {
            AlbumService.showAlbumModal(album);
        };
        $scope.onPageChanged = function () {
            pagedAlbums();
        };
        $scope.edit = function (album) {

        };
        $scope.delete = function (album) {
            MessageService.confirm({
                message: "确定需要删除吗？",
                size: "sm",
                ok: function ($modalInstance) {
                    AlbumService.delete(album).success(function (data) {
                        removeAlbum(album.id);
                        $scope.totalSize--;
                        $modalInstance.close();
                    }).error(function (data) {
                        MessageService.info({message: data.message, size: 'sm'});
                    });
                }
            });
        };
        $scope.publish = function (album) {
            AlbumService.publish(album).success(function (data) {
                album.published = true;
            });
        };
        $scope.unpublish = function (album) {
            MessageService.confirm({
                message: "确定取消发布吗？",
                size: "sm",
                ok: function ($modalInstance) {
                    AlbumService.unpublish(album).success(function (data) {
                        album.published = false;
                        $modalInstance.close();
                    }).error(function (data) {
                        MessageService.info({message: data.message, size: 'sm'});
                    });
                }
            });
        };
        $scope.addModal = function () {
            $uibModal.open({
                templateUrl: 'modules/my/albums/add.html',
                size: 'md',
                controller: 'AddAlbumController'
            });
        };

        $scope.updateModal = function (album) {

        };


    });

    angular.module("app").controller('AddAlbumController', function ($scope, AlbumService, MessageService, Upload) {


    });

})();