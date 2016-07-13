/**
 * Created by yang_shoulai on 2016/6/24.
 */

(function () {
    angular.module("app",
        [
            'ui.bootstrap',
            'ui.router',
            'ngCookies',
            'ngFileUpload',
            'angular-loading-bar',
            'ngAnimate',
            'toastr'
        ]);
    angular.module("app").run(['$rootScope', '$state', '$stateParams',
        function ($rootScope, $state, $stateParams) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
        }
    ]);
    angular.module("app").config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/home");

        $stateProvider.state('home', {
            url: "/home",
            controller: 'HomeController',
            templateUrl: "/zeal/app/modules/home/home.html",
            title:"首页 - Zeal for you"
        }).state('albums', {
            url: "/albums",
            params: {tag: 1},
            templateUrl: "/zeal/app/modules/albums/albums.html",
            controller: 'AlbumController',
            title:"美女相册 - Zeal for you"
        }).state('albums.detail', {
            url: "/album/:albumId",
            templateUrl: "/zeal/app/modules/albums/pictures.html",
            controller: 'AlbumDisplayController',
            resolve: {
                albumPromise: function (AlbumService, $stateParams) {
                    return AlbumService.getMyAlbumById($stateParams.albumId);
                }
            },
            title:"相册信息 - Zeal for you"
        }).state('movies', {
            url: "/movies",
            templateUrl: "/zeal/app/modules/movie/movies.html"
        }).state('jokes', {
            url: "/jokes",
            templateUrl: "/zeal/app/modules/jokes/jokes.html"
        }).state('stories', {
            url: "/stories",
            templateUrl: "/zeal/app/modules/story/stories.html"
        }).state('my', {
            url: "/my",
            templateUrl: "/zeal/app/modules/my/my.html",
            controller: 'MyController',
            abstract: true
        }).state('my.albums', {
            url: "/albums",
            templateUrl: "/zeal/app/modules/my/albums/albums.html",
            controller: 'MyAlbumsController',
            title:"我的相册 - Zeal for you"
        }).state('my.stories', {
            url: "/stories",
            templateUrl: "/zeal/app/modules/my/stories/stories.html"
        }).state('my.movies', {
            url: "/movies",
            templateUrl: "/zeal/app/modules/my/movies/movies.html"
        }).state('my.jokes', {
            url: "/jokes",
            templateUrl: "/zeal/app/modules/my/jokes/jokes.html"
        });
    });
    angular.module('app').config(function (cfpLoadingBarProvider) {
        cfpLoadingBarProvider.includeSpinner = true;
    });
    angular.module("app").config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('HttpInterceptor');
    }]);
    angular.module("app").config(function (toastrConfig) {
        angular.extend(toastrConfig, {
            autoDismiss: true,
            allowHtml: false,
            closeButton: true,
            progressBar: false,
            tapToDismiss: false,
            containerId: 'toast-container',
            maxOpened: 0,
            newestOnTop: true,
            positionClass: 'toast-top-right',
            preventDuplicates: false,
            preventOpenDuplicates: false,
            target: 'body',
            timeOut: 2000
        });
    });

    angular.module("app").controller('IndexController', ['UserService', '$scope', '$rootScope', '$state',
        function (UserService, $scope, $rootScope, $state) {

            $scope.isLogin = false;
            $scope.username = null;

            $scope.showLoginModal = function () {
                UserService.showLoginModal();
            };

            $scope.showRegisterModal = function () {
                UserService.showRegisterModal();
            };

            $scope.logout = function () {
                UserService.logout();
                $state.go('home');
            };

            $scope.$on('userStateChange', function (event, data) {
                if (data) {
                    $scope.isLogin = true;
                    $scope.username = data.username;
                } else {
                    $scope.isLogin = false;
                    $scope.username = null;
                }
            });

            UserService.reloadUserInfo().success(function (data) {
                $rootScope.UserInfo = {username: data.nickName};
                $rootScope.$broadcast('userStateChange', $rootScope.UserInfo);
            });
        }]);

})();
