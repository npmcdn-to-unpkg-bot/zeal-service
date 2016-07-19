/**
 * Created by Administrator on 7/19/2016.
 */
(function () {
    angular.module("register", ['zeal-common']);
    angular.module("register").controller("RegisterController", function ($scope, HttpService, MessageService, $window, $timeout, UserService) {
        $scope.user = {
            loginName: "",
            email: "",
            password: "",
            repassword: "",
            phoneNumber: "",
            validationCode: ""
        };
        $scope.validation = {};
        $scope.getVerifyCode = function () {
            UserService.getVerifyCode().success(function (data) {
                $scope.validation.imgData = "data:image/jpg;base64," + data;
            }).error(function (data) {
                MessageService.toast.error(data.message, "获取验证码失败");
            });
        };
        $scope.getVerifyCode();
        $scope.submit = function () {
            UserService.register($scope.user.loginName,
                $scope.user.email,
                $scope.user.password,
                $scope.user.phoneNumber,
                $scope.user.validationCode
            ).success(function (data) {
                MessageService.toast.success("2秒后进入主页", "注册成功");
                $timeout(function () {
                    UserService.login($scope.user.loginName, $scope.user.password)
                        .success(function () {
                            $window.location.href = "/zeal/app/index.html";
                        }).error(function (data) {
                        MessageService.toast.error(data.message, "登录失败");
                    });
                }, 2000);
            }).error(function (data) {
                MessageService.toast.error(data.message, "注册失败");
                $scope.getVerifyCode();
            });
        }

    });

})();
