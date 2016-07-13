/**
 * Created by yang_shoulai on 2016/7/2.
 */
(function () {
    angular.module("app").controller('MyAlbumsController', function ($scope, AlbumService, MessageService, $uibModal, $location) {
        $scope.page = 1;
        $scope.pageSize = 20;
        $scope.albums = [];
        $scope.totalSize = 0;
        $scope.keyword = "";
        var pagedAlbums = function () {
            AlbumService.getMyAlbums($scope.page, $scope.pageSize, $scope.keyword)
                .success(function (data) {
                    $scope.albums = data.list;
                    $scope.page = data.page;
                    $scope.pageSize = data.size;
                    $scope.totalSize = data.totalSize;
                }).error(function (data) {
                MessageService.toast.error(data.message, "获取数据失败");
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
            AlbumService.showAlbumListModal(album);
        };
        $scope.onPageChanged = function () {
            pagedAlbums();
        };
        $scope.search = function () {
            $scope.page = 1;
            pagedAlbums();
        };
        $scope.updateModal = function (album) {
            var modalInstance = $uibModal.open({
                templateUrl: '/zeal/app/modules/my/albums/update.html',
                size: 'md',
                controller: 'AlbumUpdateController',
                resolve: {
                    album: album
                }
            });
            modalInstance.closed.then(function (data) {
                pagedAlbums();
            });
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
                        MessageService.toast.success(data.message, "删除成功");
                        pagedAlbums();
                    }).error(function (data) {
                        MessageService.toast.error(data.message, "删除失败");
                    });
                }
            });
        };
        $scope.publish = function (album) {
            AlbumService.publish(album).success(function (data) {
                album.published = true;
                album.publishDate = data.publishDate;
                MessageService.toast.success("发布成功");
            }).error(function (data) {
                MessageService.toast.error(data.message, "发布失败");
            });
        };
        $scope.unpublish = function (album) {
            MessageService.confirm({
                message: "确定取消发布吗？",
                size: "sm",
                ok: function ($modalInstance) {
                    AlbumService.unpublish(album).success(function (data) {
                        album.published = false;
                        album.publishDate = data.publishDate;
                        $modalInstance.close();
                    }).error(function (data) {
                        MessageService.toast.error(data.message, "取消发布失败");
                    });
                }
            });
        };
        $scope.addModal = function () {
            var modalInstance = $uibModal.open({
                templateUrl: '/zeal/app/modules/my/albums/add.html',
                size: 'md',
                controller: 'AlbumCreateController'
            });
            modalInstance.closed.then(function (data) {
                pagedAlbums();
            });
        };
    });

    angular.module("app").controller('AlbumCreateController', function ($scope, AlbumService, MessageService, Upload, $uibModalInstance) {
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
                    MessageService.info({message: "创建成功", size: "sm"});
                    $scope.album = {};
                    $scope.pictures = [];
                    $scope.progress = 0;
                    $uibModalInstance.close();
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


    angular.module("app").controller('AlbumUpdateController', function ($scope, AlbumService, MessageService, Upload, $uibModalInstance, album) {
        var albumId = album.id;
        $scope.name = album.name;
        $scope.description = album.description;
        $scope.existPictures = album.pictures;
        $scope.pictures = [];
        $scope.uploading = false;
        $scope.progress = 0;
        var deleteList = [];
        var tags = album.tags ? album.tags : [];
        $scope.allTags = [];
        $scope.tagExist = function (tag) {
            for (var i = 0; i < tags.length; i++) {
                if (tags[i].id == tag.id) {
                    return true;
                }
            }
            return false;
        };

        $scope.toggleTag = function (tag) {
            if ($scope.tagExist(tag)) {
                for (var i = 0; i < tags.length; i++) {
                    if (tag.id == tags[i].id) {
                        tags.splice(i, 1);
                    }
                }
            } else {
                tags.push(tag);
            }
        };

        AlbumService.getAllChildrenTags().success(function (data) {
            $scope.allTags = data
        });


        $scope.deleteExistPicture = function (picture) {
            for (var i = 0; i < $scope.existPictures.length; i++) {
                if (picture == $scope.existPictures[i]) {
                    $scope.existPictures.splice(i, 1);
                    deleteList.push(picture.id);
                    return;
                }
            }
        };
        $scope.deletePicture = function (picture) {
            for (var i = 0; i < $scope.pictures.length; i++) {
                if (picture == $scope.pictures[i]) {
                    $scope.pictures.splice(i, 1);
                    return;
                }
            }
        };
        $scope.showPicture = function (picture) {
            AlbumService.showPicture(picture);
        };

        var arrayToString = function (array) {
            if (array || array.length > 0) {
                var buffer = "";
                for (var i = 0; i < array.length; i++) {
                    buffer = buffer + array[i] + ",";
                }
                if (buffer.length > 2) {
                    buffer = buffer.substring(0, buffer.length - 1);
                }
                return buffer;
            }
            return null;
        };


        $scope.update = function () {
            var tagIdArray = [];
            for (var i = 0; i < tags.length; i++) {
                tagIdArray.push(tags[i].id);
            }

            $scope.uploading = true;
            var request = {
                id: albumId,
                name: $scope.name,
                description: $scope.description == null ? "" : $scope.description,
                deletes: arrayToString(deleteList),
                files: $scope.pictures,
                tags: arrayToString(tagIdArray)
            };
            Upload.upload({
                url: "/zeal/album/update",
                data: request
            }).then(function (resp) {
                $scope.uploading = false;
                var data = resp.data;
                if (data.status == 1) {
                    //MessageService.info({message: "更新成功", size: "sm"});
                    $scope.pictures = [];
                    $scope.progress = 0;
                    $uibModalInstance.close();
                } else {
                    MessageService.info({message: data.message, size: "sm"});
                }
            }, function (data) {
                $scope.uploading = false;
                MessageService.info({message: "更新失败", size: "sm"});
            }, function (evt) {
                $scope.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
        };


    })
})();