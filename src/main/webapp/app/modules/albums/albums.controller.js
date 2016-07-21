/**
 * Created by yang_shoulai on 2016/6/25.
 */

(function () {
    angular.module("app").controller('AlbumController', ['$scope', 'HttpService', '$log', '$state', '$uibModal', 'AlbumService', '$stateParams', '$location', 'childTags',
        function ($scope, HttpService, $log, $state, $uibModal, AlbumService, $stateParams, $location, childTags) {
            $scope.pagination = {
                page: 1,
                totalSize: 0,
                pageSize: 24
            };
            $scope.childTags = childTags;
            if (childTags && childTags.length > 0) {
                $scope.childTagId = childTags[0].id;
                $state.current.title = childTags[0].name + " - 美女相册";
            }
            $scope.targetAlbums = [];
            $scope.getAlbums = function () {
                AlbumService.getPublishedAlbums($scope.pagination.page, $scope.pagination.pageSize, $scope.childTagId).success(function (data) {
                    $scope.targetAlbums = [];
                    $scope.pagination.page = data.page;
                    $scope.pagination.totalSize = data.totalSize;
                    var albums = data.list;
                    if (albums && albums.length > 0) {
                        for (var i = 0; i < albums.length; i++) {
                            $scope.targetAlbums.push(albums[i]);
                        }
                    }
                    window.scrollTo(0, 0);
                }).error(function (data) {
                    $log.info(data.message);
                });
            };

            $scope.onPageChanged = function () {
                $scope.getAlbums();
            };

            $scope.onClickAlbum = function (album) {
                $location.path('/albums/album/' + album.id);
            };

            $scope.childTagClick = function (childTag) {
                $state.current.title = childTag.name + " - 美女相册";
                $scope.childTagId = childTag.id;
                $scope.pagination.page = 1;
                $scope.pagination.totalSize = 0;
                $scope.targetAlbums = [];
                $scope.getAlbums();
            };

            $scope.getAlbums();
        }]);


    angular.module("app").controller('AlbumDisplayController', [
        '$scope', 'HttpService', '$log', '$stateParams', 'AlbumService', 'album', 'author', 'MessageService', '$state', 'UserService',
        function ($scope, HttpService, $log, $stateParams, AlbumService, album, author, MessageService, $state, UserService) {
            $scope.album = album;
            $scope.author = author;
            $state.current.title = album.name;
            $scope.collectAlbum = function (album) {
                AlbumService.collect(album.id).success(function (data) {
                    $scope.album.collected = true;
                    $scope.album.collectionCount++;
                }).error(function (data) {
                    MessageService.toast.error(data.message, "收藏失败");
                    $scope.album.collected = false;
                });
            };

            $scope.uncollectAlbum = function (album) {
                AlbumService.uncollect(album.id).success(function (data) {
                    $scope.album.collected = false;
                    $scope.album.collectionCount--;
                }).error(function (data) {
                    MessageService.toast.error(data.message, "取消收藏失败");
                    $scope.album.collected = true;
                });
            };

            $scope.toggleCollect = function () {
                if ($scope.album) {
                    if ($scope.album.collected) {
                        $scope.uncollectAlbum($scope.album);
                    } else {
                        $scope.collectAlbum($scope.album);
                    }
                }
            };
            /**
             * 点赞
             */
            $scope.appreciate = function (album) {
                AlbumService.appreciate(album.id).success(function (data) {
                    $scope.album.appreciated = true;
                    $scope.album.appreciationCount++;
                }).error(function (data) {
                    MessageService.toast.error(data.message, "点赞失败");
                    $scope.album.appreciated = false;
                });
            };

            /**
             * 取消点赞
             */
            $scope.unAppreciate = function (album) {
                AlbumService.unAppreciate(album.id).success(function (data) {
                    $scope.album.appreciated = false;
                    $scope.album.appreciationCount--;
                }).error(function (data) {
                    MessageService.toast.error(data.message, "取消点赞失败");
                    $scope.album.appreciated = true;
                });
            };

            $scope.toggleAppreciate = function () {
                if ($scope.album) {
                    if ($scope.album.appreciated) {
                        $scope.unAppreciate($scope.album);
                    } else {
                        $scope.appreciate($scope.album);
                    }
                }
            };

            $scope.toggleAppreciateAuthor = function () {
                if ($scope.author) {
                    if ($scope.author.appreciated) {
                        AlbumService.unAppreciateAuthor($scope.author.id).success(function (data) {
                            $scope.author.appreciationCount--;
                            $scope.author.appreciated = false;
                        }).error(function (data) {
                            MessageService.toast.error(data.message, "取消点赞失败");
                        });
                    } else {
                        AlbumService.appreciateAuthor($scope.author.id).success(function (data) {
                            $scope.author.appreciationCount++;
                            $scope.author.appreciated = true;
                        }).error(function (data) {
                            MessageService.toast.error(data.message, "点赞失败");
                        });
                    }
                }
            };

            $scope.toggleFollow = function () {
                if ($scope.author) {
                    if ($scope.author.followed) {
                        UserService.cancelFollow($scope.author.id).success(function (data) {
                            $scope.author.followerCount--;
                            $scope.author.followed = false;
                        }).error(function (data) {
                            MessageService.toast.error(data.message, "取消关注失败");
                        });
                    } else {
                        UserService.follow($scope.author.id).success(function (data) {
                            $scope.author.followerCount++;
                            $scope.author.followed = true;
                        }).error(function (data) {
                            MessageService.toast.error(data.message, "关注失败");
                        });
                    }
                }
            };


        }]);

    angular.module("app").controller('AlbumListDisplayController', ['$scope', 'HttpService', '$log', '$stateParams', 'CookieService', 'album',
        function ($scope, HttpService, $log, $stateParams, CookieService, album) {
            $scope.album = album;
        }]);

    angular.module("app").controller('PictureDisplayController', function ($scope, picture) {
        $scope.picture = picture;
    });
})();
