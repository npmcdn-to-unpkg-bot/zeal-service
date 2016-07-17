/**
 * Created by yang_shoulai on 2016/7/2.
 */
(function () {
    angular.module("app").controller('MyAlbumsController',
        function ($scope, AlbumService, MessageService, $uibModal, $location, $rootScope, UserService) {
            $scope.pagination = {
                pageSize: 24,
                page: 1,
                totalSize: 0,
                keyword: ""
            };
            $scope.publishStates = [
                {name: "全部", value: -1},
                {name: "已发布", value: 1},
                {name: "未发布", value: 0}
            ];
            $scope.stateSelected = {value: $scope.publishStates[0]};
            $scope.onPublishStateChange = function (item, model) {
                $scope.pagination.page = 1;
                $scope.pagination.pageSize = 24;
                $scope.pagination.keyword = "";
                pagedAlbums();
            };
            $scope.albums = [];
            var pagedAlbums = function () {
                AlbumService.getMyAlbums($scope.pagination.page, $scope.pagination.pageSize, $scope.pagination.keyword, $scope.stateSelected.value.value)
                    .success(function (data) {
                        $scope.albums = data.list;
                        $scope.pagination.page = data.page;
                        $scope.pagination.pageSize = data.size;
                        $scope.pagination.totalSize = data.totalSize;

                        window.scrollTo(0, 0);
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
                $location.path("/my/albums/view/" + album.id);
            };
            $scope.onPageChanged = function () {
                pagedAlbums();
            };
            $scope.search = function () {
                $scope.pagination.page = 1;
                pagedAlbums();
            };
            $scope.updateModal = function (album) {
                if (album.published) {
                    MessageService.toast.info("相册已经发布，无法编辑");
                    return;
                }
                var modalInstance = $uibModal.open({
                    templateUrl: '/zeal/app/modules/my/albums/update.html',
                    size: 'md',
                    keyboard: false,
                    controller: 'AlbumUpdateController',
                    resolve: {
                        album: album
                    }
                });
                modalInstance.result.then(function (data) {
                    for (var i = 0; i < $scope.albums.length; i++) {
                        if ($scope.albums[i].id == data.id) {
                            $scope.albums[i] = data;
                            return;
                        }
                    }
                    //pagedAlbums();
                });
            };
            $scope.delete = function (album) {
                if (album.published) {
                    MessageService.toast.info("相册已经发布，无法删除");
                    return;
                }
                MessageService.confirm({
                    message: "确定需要删除吗？",
                    size: "sm",
                    ok: function ($modalInstance) {
                        AlbumService.delete(album).success(function (data) {
                            $scope.pagination.totalSize--;
                            $modalInstance.close();
                            $scope.$emit('album-deleted', {});
                            MessageService.toast.success("删除成功");
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
                    $scope.$emit('publish-status-change', {published: true});
                    if ($scope.stateSelected.value.value >= 0) {
                        pagedAlbums();
                    }

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
                            $scope.$emit('publish-status-change', {published: false});
                            if ($scope.stateSelected.value.value >= 0) {
                                pagedAlbums();
                            }
                        }).error(function (data) {
                            MessageService.toast.error(data.message, "取消发布失败");
                        });
                    }
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
                    MessageService.toast.success("更新成功");
                    $scope.pictures = [];
                    $scope.progress = 0;
                    $uibModalInstance.close(data.result);
                } else {
                    MessageService.toast.error(data.message);
                }
            }, function (data) {
                $scope.uploading = false;
                MessageService.toast.error("更新失败");
            }, function (evt) {
                $scope.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
        };
    })


    angular.module("app").controller('MyAlbumViewController', function ($scope, album) {
        $scope.album = album;
    });
})();