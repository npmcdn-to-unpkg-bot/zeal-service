/**
 * Created by yang_shoulai on 2016/6/25.
 */
(function () {
    angular.module("zeal-common").service('UserService', ['$uibModal', '$rootScope', 'CookieService', 'HttpService',
        function ($uibModal, $rootScope, CookieService, HttpService) {
            /**
             * 用户登出
             * @returns {*|{promise, success, error}}
             */
            this.logout = function () {
                return HttpService.http({url: "/zeal/userinfo/logout"});
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

            /**
             * 用户注册
             */
            this.register = function (loginName, email, password, phoneNumber, validationCode) {
                return HttpService.http({
                    method: "POST",
                    url: "/zeal/userinfo/register",
                    data: {
                        loginName: loginName,
                        email: email,
                        password: password,
                        phoneNumber: phoneNumber,
                        verifyCode: validationCode
                    }
                });
            };

            /**
             * 获取注册验证码
             */
            this.getVerifyCode = function () {
                return HttpService.http({
                    method: "GET",
                    url: "/zeal/userinfo/validationCode"
                });
            };

        }]);
})();