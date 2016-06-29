/**
 * Created by yang_shoulai on 2016/6/25.
 */
(function () {
    angular.module("app").service('UserService', ['$uibModal', '$rootScope', 'CookieService', '$http', function ($uibModal, $rootScope, CookieService, $http) {
        /**
         * 显示登录窗口
         */
        this.showLoginModal = function () {
            $uibModal.open({
                templateUrl: 'modules/user/login.html',
                size: 'md',
                controller: 'LoginController'
            });
        };

        /**
         * 显示注册窗口
         */
        this.showRegisterModal = function () {
            $uibModal.open({
                templateUrl: 'modules/user/register.html',
                size: 'md',
                controller: 'RegisterController'
            });
        };


        this.logout = function () {
            $http.get("http://www.baidu.com").success(function () {
                $rootScope.UserInfo = null;
                $rootScope.$broadcast('userStateChange', null);
            }).error(function () {
                
            });


        };

        this.login = function (username, password, rememberMe) {
            var user = {username: "杨寿来", password: password};
            if (rememberMe) {
                CookieService.put(CookieService.KEY_CONSTANT.user_login_remember_me, 1);
                CookieService.put(CookieService.KEY_CONSTANT.user_login_username, username);
                CookieService.put(CookieService.KEY_CONSTANT.user_login_password, password);
            } else {
                CookieService.put(CookieService.KEY_CONSTANT.user_login_remember_me, 0);
                CookieService.remove(CookieService.KEY_CONSTANT.user_login_username);
                CookieService.remove(CookieService.KEY_CONSTANT.user_login_password);
            }
            $rootScope.UserInfo = user;
            $rootScope.$broadcast('userStateChange', user);
        };


    }]);
})();