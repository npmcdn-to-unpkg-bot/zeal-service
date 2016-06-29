/**
 * Created by yang_shoulai on 2016/6/25.
 */

(function () {
    angular.module("app").controller('LoginController', ['$scope', '$rootScope', '$uibModalInstance', 'CookieService', 'UserService', '$state',
        function ($scope, $rootScope, $uibModalInstance, CookieService, UserService, $state) {
            $scope.errorMsg     = "";
            //取消登录窗口
            $scope.cancel       = function () {
                $uibModalInstance.close();
            };
            $scope.loginRequest = {
                username: "",
                password: "",
                rememberMe: false
            };
            if (CookieService.get(CookieService.KEY_CONSTANT.user_login_remember_me) == 1) {
                $scope.loginRequest.username   = CookieService.get(CookieService.KEY_CONSTANT.user_login_username);
                $scope.loginRequest.password   = CookieService.get(CookieService.KEY_CONSTANT.user_login_password);
                $scope.loginRequest.rememberMe = true;
            }

            $scope.login = function () {
                if ($scope.loginRequest.username == "" || $scope.loginRequest.password == "") {
                    $scope.errorMsg = "请输入用户名和密码";
                    return;
                }
                $scope.errorMsg = "";
                UserService.login($scope.loginRequest.username, $scope.loginRequest.password, $scope.loginRequest.rememberMe);
                $uibModalInstance.close();
                $state.go('home');
            };


        }
    ])
    ;

    angular.module("app").controller('RegisterController', ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {


    }]);

})();
