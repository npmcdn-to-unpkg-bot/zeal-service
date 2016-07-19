/**
 * Created by Administrator on 7/19/2016.
 */
(function () {
    angular.module("login", ['zeal-common']);
    angular.module("login").controller("LoginController", function ($scope, CookieService, MessageService, $window, $timeout, UserService) {
        $scope.request = {
            loginName: "",
            password: "",
            remember: false
        };
        if (CookieService.get(CookieService.KEY_CONSTANT.user_login_remember_me) == 1) {
            $scope.request.loginName = CookieService.get(CookieService.KEY_CONSTANT.user_login_username);
            $scope.request.password = CookieService.get(CookieService.KEY_CONSTANT.user_login_password);
            $scope.request.remember = true;
        }
        $scope.loginNameChange = function () {
            $scope.request.password = '';
        };
        $scope.submit = function () {
            UserService.login($scope.request.loginName, $scope.request.password)
                .success(function (data) {
                    MessageService.toast.success(data.message, "登录成功");
                    if ($scope.request.remember) {
                        CookieService.put(CookieService.KEY_CONSTANT.user_login_remember_me, 1);
                        CookieService.put(CookieService.KEY_CONSTANT.user_login_username, $scope.request.loginName);
                        CookieService.put(CookieService.KEY_CONSTANT.user_login_password, $scope.request.password);
                    } else {
                        CookieService.put(CookieService.KEY_CONSTANT.user_login_remember_me, 0);
                        CookieService.remove(CookieService.KEY_CONSTANT.user_login_username);
                        CookieService.remove(CookieService.KEY_CONSTANT.user_login_password);
                    }
                    $timeout(function () {
                        $window.location.href = "/zeal/app/index.html";
                    }, 1000);
                }).error(function (data) {
                MessageService.toast.error(data.message, "登录失败");
            });
        };
    });
})();