/**
 * Created by yang_shoulai on 2016/7/5.
 */
(function () {
    angular.module("app").directive("albumList", function () {
        return {
            restrict: "E",
            transclude: false,
            scope: {
                pictures: "=pictures"
            },
            controller: ['$scope', '$compile', '$document', function ($scope, $compile, $document) {
                $scope.index = 0;
                $scope.clickPic = function (index) {
                    $scope.index = index;
                    var doc = angular.element('<div class="_mask" onselectstart="return false;">' +
                        '<div class="_mask-bg">' +
                        '</div>' +
                        '<span class="glyphicon glyphicon-remove closer" ng-click="_close()"></span>' +
                        '<div class="_mask-content">' +
                        '<img ng-src="{{pictures[index].url}}">' +
                        '</div>' +
                        '<span class="glyphicon glyphicon-chevron-left slider slider-left" ng-hide="index <= 0" ng-click="_pre()"></span>' +
                        '<span class="glyphicon glyphicon-chevron-right slider slider-right" ng-hide="index >= pictures.length - 1"  ng-click="_next()"></span>' +
                        '</div>')[0];
                    document.body.appendChild(doc);
                    $compile(doc)($scope);
                };
                $scope._pre = function () {
                    if ($scope.index > 0) {
                        $scope.index--;
                    }
                };
                $scope._next = function () {
                    if ($scope.index < $scope.pictures.length - 1) {
                        $scope.index++;
                    }
                };
                $scope._close = function () {
                    document.body.removeChild(document.getElementsByClassName("_mask")[0]);
                };


            }],
            templateUrl: "/zeal/app/common/tpls/tpl-album-list.html",
            replace: true
        };
    });
})();