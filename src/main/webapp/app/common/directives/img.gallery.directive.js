/**
 * Created by yang_shoulai on 2016/7/17.
 */
(function () {
    angular.module("app").directive('imgGallery', function () {
        return {
            restrict: "E",
            transclude: false,
            scope: {
                pictures: "=pictures",
                index: "@index",
                style: "@style",
                class: "@class",
                src: "@imgSrc"
            },
            controller: ['$scope', '$compile', '$document', '$rootScope', 'MessageService', function ($scope, $compile, $document, $rootScope, MessageService) {
                $scope.index = parseInt($scope.index);
                $scope.clickPic = function () {
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
                    $compile(doc)($scope);
                    document.body.appendChild(doc);
                    $document.on('keydown', keyDownEventListener);
                    $rootScope.$on('$destroy', function () {
                        $document.off('keydown', keydownListener);
                    });
                };
                $scope._pre = function () {
                    if ($scope.index > 0) {
                        $scope.index--;
                    } else {
                        MessageService.toast.info("已经是第一张");
                    }
                };
                $scope._next = function () {
                    if ($scope.index < $scope.pictures.length - 1) {
                        $scope.index++;
                    } else {
                        MessageService.toast.info("已经是最后一张");
                    }
                };
                $scope._close = function () {
                    document.body.removeChild(document.getElementsByClassName("_mask")[0]);
                    $document.off('keydown', keyDownEventListener);
                };

                var keyDownEventListener = function (evt) {
                    if (!evt.isDefaultPrevented()) {
                        evt.preventDefault();
                    }
                    switch (evt.which) {
                        case 27: //ESC
                            $scope._close();
                            break;
                        case 37: //LEFT
                            if ($scope.index > 0) {
                                $scope.$apply(function () {
                                    $scope.index--;
                                });

                            } else {
                                MessageService.toast.info("已经是第一张");
                            }
                            break;
                        case 39: //RIGHT
                            if ($scope.index < $scope.pictures.length - 1) {
                                $scope.$apply(function () {
                                    $scope.index++;
                                });
                            } else {
                                MessageService.toast.info("已经是最后一张");
                            }
                            break;
                    }
                };
            }],
            template: '<img ng-src="{{src}}" style="{{style}}" class="pointer {{class}}" ng-click="clickPic()">',
            replace: true
        };
    });
})();
