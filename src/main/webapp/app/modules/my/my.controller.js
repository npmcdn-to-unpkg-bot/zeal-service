/**
 * Created by Administrator on 7/1/2016.
 */
(function () {
    angular.module("app").controller('MyController', function ($scope, myAuthor, $location) {
        $scope.zealInfo = myAuthor;

        $scope.addModal = function () {
            $location.path("/my/albums/add");
            /* var modalInstance = $uibModal.open({
             templateUrl: '/zeal/app/modules/my/albums/add.html',
             size: 'md',
             controller: 'AlbumCreateController'
             });*/
        };

        /**
         * 监听相册的发布状态改变，已更新相册发布或者未发布的数量
         */
        $scope.$on('publish-status-change', function (event, data) {
            if (data) {
                if (data.published) {
                    $scope.zealInfo.publishedCount++;
                    $scope.zealInfo.unpublishedCount--;
                } else {
                    $scope.zealInfo.publishedCount--;
                    $scope.zealInfo.unpublishedCount++;
                }
            }
        });

        /**
         * 监听相册的删除事件
         */
        $scope.$on('album-deleted', function (event, data) {
            $scope.zealInfo.unpublishedCount--;
        });

        /**
         * 监听相册的新增事件
         */
        $scope.$on('album-created', function (event, data) {
            $scope.zealInfo.unpublishedCount++;
        });

        /**
         * 监听收藏相册的删除事件
         */
        $scope.$on('album-collection-delete', function (event, data) {
            $scope.zealInfo.collectionCount--;
        });

    });

    angular.module("app").controller('AlbumCreateController', function ($scope, AlbumService, MessageService, Upload, $state) {
        $scope.album = {};
        $scope.pictures = [];
        $scope.progress = 0;

        $scope.uploading = false;

        $scope.select = function (files) {
            if (files && files.length) {
                $scope.pictures = files;
            }
        };

        $scope.deletePicture = function (picture) {
            if ($scope.pictures && $scope.pictures.length > 0) {
                for (var i = 0; i < $scope.pictures.length; i++) {
                    if (picture == $scope.pictures[i]) {
                        $scope.pictures.splice(i, 1);
                        return;
                    }
                }
            }
        };

        $scope.create = function () {
            $scope.uploading = true;
            var request = {name: $scope.album.name, description: $scope.album.description};
            for (var i = 0; i < $scope.pictures.length; i++) {
                request['pictures[' + i + ']'] = $scope.pictures[i];
            }
            Upload.upload({
                url: "/zeal/album/create",
                data: request
            }).then(function (resp) {
                $scope.uploading = false;
                var data = resp.data;
                if (data.status == 1) {
                    MessageService.toast.success("创建成功");
                    $scope.album = {};
                    $scope.pictures = [];
                    $scope.progress = 0;
                    $scope.$emit('album-created', {});
                    $state.reload();
                } else {
                    MessageService.info({message: data.message, size: "sm"});
                }
            }, function (data) {
                $scope.uploading = false;
                MessageService.info({message: "创建失败", size: "sm"});
            }, function (evt) {
                $scope.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
        };

    });
})();