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

})();

