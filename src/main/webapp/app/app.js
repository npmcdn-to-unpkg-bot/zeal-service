/**
 * Created by yang_shoulai on 2016/6/24.
 */

(function () {
    angular.module("app", ['ui.bootstrap', 'ui.router', 'ngCookies']);
    angular.module("app").config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/home");

        $stateProvider.state('home', {
            url: "/home",
            templateUrl: "modules/home/home.html"
        }).state('pictures', {
            url: "/pictures",
            templateUrl: "modules/picture/pictures.html",
            controller: 'PictureController'
        }).state('movies', {
            url: "/movies",
            templateUrl: "modules/movie/movies.html"
        }).state('jokes', {
            url: "/jokes",
            templateUrl: "modules/jokes/jokes.html"
        }).state('stories', {
            url: "/stories",
            templateUrl: "modules/story/stories.html"
        }).state('my', {
            url: "/my",
            templateUrl: "modules/my/my.html"
        });
    });

    angular.module("app").config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('HttpInterceptor');
    }]);

    angular.module("app").controller('IndexController', ['UserService', '$scope', function (UserService, $scope) {

        $scope.isLogin  = false;
        $scope.username = null;

        $scope.showLoginModal = function () {
            UserService.showLoginModal();
        };

        $scope.showRegisterModal = function () {
            UserService.showRegisterModal();
        };

        $scope.logout = function () {
            UserService.logout();
        };

        $scope.$on('userStateChange', function (event, data) {
            if (data) {
                $scope.isLogin  = true;
                $scope.username = data.username;
            } else {
                $scope.isLogin  = false;
                $scope.username = null;
            }
        });
    }]);

})();
