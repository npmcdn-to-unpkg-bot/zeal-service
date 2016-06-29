/**
 * Created by yang_shoulai on 2016/6/25.
 */

(function () {
    angular.module("app").controller('PictureController', ['$scope', '$http', function ($scope, $http) {
        $scope.albums = [];
        $http({
            method: "GET",
            url: "/zeal/album/published?page=1&pageSize=40"
        }).success(function (data) {
            var albums = data.result.list;
            if (albums && albums.length > 0) {
                for (var i = 0; i < albums.length; i++) {
                    if (i % 6 == 0) {
                        $scope.albums.push([]);
                    }
                    var album = albums[i];
                    $scope.albums[(i - i % 6) / 6].push(
                        {
                            name: album.name,
                            size: album.pictures.length,
                            pictures: album.pictures,
                            url: album.pictures[0].url
                        }
                    );
                }
            }
        }).error(function (data) {

        });
    }]);
})();
