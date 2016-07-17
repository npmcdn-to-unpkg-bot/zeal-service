/**
 * Created by yang_shoulai on 2016/6/25.
 */
(function () {
    angular.module("app").service('UserService', ['$uibModal', '$rootScope', 'CookieService', 'HttpService',
        function ($uibModal, $rootScope, CookieService, HttpService) {
            /**
             * 显示登录窗口
             */
            this.showLoginModal = function () {
                $uibModal.open({
                    templateUrl: '/zeal/app/modules/user/login.html',
                    size: 'md',
                    controller: 'LoginController'
                });
            };

            /**
             * 显示注册窗口
             */
            this.showRegisterModal = function () {
                $uibModal.open({
                    templateUrl: '/zeal/app/modules/user/register.html',
                    size: 'md',
                    controller: 'RegisterController'
                });
            };


            this.logout = function () {
                HttpService.http({url: "/zeal/userinfo/logout"}).success(function () {
                    $rootScope.UserInfo = null;
                    $rootScope.$broadcast('userStateChange', null);
                }).error(function () {

                });


            };

            /**
             * 用戶登录
             * @param username
             * @param password
             * @returns {*|{promise, success, error}}
             */
            this.login = function (username, password) {
                return HttpService.http({
                    method: "POST",
                    data: {loginName: username, password: password},
                    url: "/zeal/userinfo/login"
                });
            };

            /**
             * 重新加载用户信息，如果用户会话失效返回null,若用户没有失效，则拉取用户信息
             * @returns {*|{promise, success, error}}
             */
            this.reloadUserInfo = function () {
                return HttpService.http({
                    method: "POST",
                    url: "/zeal/userinfo/reload"
                });
            };

            /**
             * 获取我的zeal信息
             * @returns {*|{promise, success, error}}
             */
            this.getMyZealInfo = function () {
                return HttpService.http({
                    method: "GET",
                    url: "/zeal/my/zealInfo"
                });
            }

            /**
             * 获取用户的一些zeal信息
             * @param userInfoId
             * @returns {*|{promise, success, error}}
             */
            this.getAuthorInfo = function (userInfoId) {
                return HttpService.http({
                    method: "GET",
                    url: "/zeal/userinfo/author/" + userInfoId
                });
            };


        }]);
})();