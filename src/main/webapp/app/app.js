/**
 * Created by yang_shoulai on 2016/6/24.
 */

(function () {
    angular.module("app", ['ui.bootstrap', 'ui.router', 'ngCookies', 'ngFileUpload']);
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
            templateUrl: "/zeal/app/modules/home/home.html"
        }).state('albums', {
            url: "/albums",
            templateUrl: "/zeal/app/modules/albums/albums.html",
            controller: 'AlbumController'
        }).state('pictures', {
            url: "/pictures",
            templateUrl: "/zeal/app/modules/albums/pictures.html",
            controller: 'AlbumDisplayController',
            params: {album: null}
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
            controller: 'MyAlbumsController'
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

    angular.module("app").config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('HttpInterceptor');
    }]);

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
