/**
 * Created by yang_shoulai on 2016/7/21.
 */
(function () {

    angular.module('app').controller('MyUserInfoBasicController', function ($scope, basicInfo, UserService, MessageService, $window) {

        $scope.request = {
            nickName: basicInfo.nickName,
            email: basicInfo.email,
            phoneNumber: basicInfo.phoneNumber,
            description: basicInfo.description
        };

        $scope.submit = function () {
            UserService.updateBaiscInfo($scope.request).success(function (data) {
                MessageService.toast.success("更新成功");
                $window.location.reload();
            }).error(function (data) {
                MessageService.toast.error(data.message, "更新失败");
            });
        };
    });

    angular.module('app').controller('MyUserInfoPasswordController', function ($scope, UserService, MessageService) {
        $scope.request = {
            password: "",
            newPassword: "",
            reNewPassword: ""
        };

        $scope.submit = function () {
            UserService.updatePassword({
                password: $scope.request.password,
                newPassword: $scope.request.newPassword
            }).success(function (data) {
                MessageService.toast.success("更新成功");
            }).error(function (data) {
                MessageService.toast.error(data.message, "更新失败");
            });
        };
    });

    angular.module('app').controller('MyUserInfoPhotoController', function ($scope, UserService, MessageService, $window) {
        $scope.fileChanged = function (e) {
            var files = e.target.files;
            var fileReader = new FileReader();
            fileReader.readAsDataURL(files[0]);
            fileReader.onload = function (e) {
                $scope.imgSrc = this.result;
                $scope.$apply();
            };
        }

        $scope.clear = function () {
            $scope.imageCropStep = 1;
            delete $scope.imgSrc;
            delete $scope.result;
            delete $scope.resultBlob;
        };

        $scope.submit = function () {
            if (!$scope.result || $scope.result.length <= 0) {
                MessageService.toast.error(data.message, "请选择一张图片");
                return;
            }
            UserService.updatePhoto($scope.result).success(function (data) {
                MessageService.toast.success("更新成功");
                $window.location.reload();
            }).error(function (data) {
                MessageService.toast.error(data.message, "更新失败");
            });
        };
    });

})();

