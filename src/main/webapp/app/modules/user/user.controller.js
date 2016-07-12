/**
 * Created by yang_shoulai on 2016/6/25.
 */

(function () {
    angular.module("app").controller('LoginController', ['$scope', '$rootScope', '$uibModalInstance', 'CookieService', 'UserService', '$state', '$log',
        function ($scope, $rootScope, $uibModalInstance, CookieService, UserService, $state, $log) {
            $scope.errorMsg = "";
            //取消登录窗口
            $scope.cancel = function () {
                $uibModalInstance.close();
            };
            $scope.loginRequest = {
                username: "",
                password: "",
                rememberMe: false
            };
            if (CookieService.get(CookieService.KEY_CONSTANT.user_login_remember_me) == 1) {
                $scope.loginRequest.username = CookieService.get(CookieService.KEY_CONSTANT.user_login_username);
                $scope.loginRequest.password = CookieService.get(CookieService.KEY_CONSTANT.user_login_password);
                $scope.loginRequest.rememberMe = true;
            }

            $scope.login = function () {
                if ($scope.loginRequest.username == "" || $scope.loginRequest.password == "") {
                    $scope.errorMsg = "请输入用户名和密码";
                    return;
                }
                $scope.errorMsg = "";
                UserService.login($scope.loginRequest.username, $scope.loginRequest.password)
                    .success(function (data) {
                        if ($scope.loginRequest.rememberMe) {
                            CookieService.put(CookieService.KEY_CONSTANT.user_login_remember_me, 1);
                            CookieService.put(CookieService.KEY_CONSTANT.user_login_username, $scope.loginRequest.username);
                            CookieService.put(CookieService.KEY_CONSTANT.user_login_password, $scope.loginRequest.password);
                        } else {
                            CookieService.put(CookieService.KEY_CONSTANT.user_login_remember_me, 0);
                            CookieService.remove(CookieService.KEY_CONSTANT.user_login_username);
                            CookieService.remove(CookieService.KEY_CONSTANT.user_login_password);
                        }
                        $rootScope.UserInfo = {username: data.nickName, password: $scope.loginRequest.password};
                        $rootScope.$broadcast('userStateChange', $rootScope.UserInfo);
                        $uibModalInstance.close();
                        $state.go('my.albums', null, {reload: true});
                    }).error(function (data) {
                    alert(data.message);
                    $log.info(data.message);
                });
            };


        }
    ])
    ;

    angular.module("app").controller('RegisterController', ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {


    }]);

})();
