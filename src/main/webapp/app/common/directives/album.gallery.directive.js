/**
 * Created by yang_shoulai on 2016/7/5.
 */
(function () {
    angular.module("app").directive("albumGallery", function () {
        return {
            restrict: "E",
            transclude: false,
            scope: {
                pictures: "=pictures"
            },
            controller: ['$scope', function ($scope) {
                $scope.fullScreen = function () {
                    if (document.webkitIsFullScreen) {
                        document.webkitCancelFullScreen()
                    } else {
                        document.getElementsByClassName("album-container")[0].webkitRequestFullScreen();
                    }
                };

                $scope.close = function () {
                    if (document.webkitIsFullScreen) {
                        document.webkitCancelFullScreen()
                    }
                    var div = document.getElementById("album-container");
                    if (div) {
                        div[0].parentNode.removeChild(div[0]);
                        document.getElementsByTagName("body")[0].removeChild(div);
                    }
                };

                $scope.index = 0;
                $scope.subIndex = 0;
                $scope.pre = function () {
                    if ($scope.index > 0) {
                        $scope.index--;
                        if ($scope.index >= 5) {
                            if ($scope.subIndex > $scope.index) {
                                $scope.subIndex = $scope.index - 5;
                            } else if ($scope.subIndex + 6 <= $scope.index) {
                                $scope.subIndex = $scope.index - 5;
                            }
                        } else {
                            $scope.subIndex = 0;
                        }
                    } else {
                        $scope.subIndex = 0;
                    }
                };
                $scope.next = function () {
                    console.log(1);
                    if ($scope.index < $scope.pictures.length - 1) {
                        $scope.index++;
                        if ($scope.index - 6 >= $scope.subIndex) {
                            if ($scope.subIndex + 6 <= $scope.pictures.length) {
                                $scope.subIndex = $scope.index;
                            } else {
                                $scope.subIndex = $scope.pictures.length - 6;
                            }
                        } else if ($scope.index < $scope.subIndex) {
                            $scope.subIndex = $scope.index;
                        }
                    }
                };
                $scope.select = function (index) {
                    $scope.index = index;
                };

                $scope.subIndexPre = function () {
                    if ($scope.subIndex > 0) {
                        $scope.subIndex--;
                    }
                };

                $scope.subIndexNext = function () {
                    if ($scope.subIndex < $scope.pictures.length - 6) {
                        $scope.subIndex++;
                    }
                };


            }],
            templateUrl: "/zeal/app/common/tpls/tpl-album-gallery.html",
            replace: true
        };
    });
})();