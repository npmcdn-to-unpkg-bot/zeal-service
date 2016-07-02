/**
 * Created by yang_shoulai on 2016/6/25.
 */

(function () {
    angular.module("app").service("MessageService", function ($uibModal) {
        var defaultSettings = {
            title: "提示",
            message: "",
            okText: "确定",
            cancelText: "取消",
            ok: function ($modalInstance) {
                $modalInstance.close();
            },
            cancel: function ($modalInstance) {
                $modalInstance.close();
            },
            size: "md",
            okDisplay: true,
            cancelDisplay: true
        };

        var messgae = function (settings) {
            if (!settings.type || !(settings.type == "info" || settings.type == "confirm")) {
                settings.type = "info";
            }
            if (settings.size != "sm" && settings.size != "md" && settings.size != "lg") {
                settings.size = "md";
            }
            var $modalInstance = $uibModal.open({
                templateUrl: "/zeal/app/common/tpls/tpl-message-box.html",
                controller: function ($scope) {
                    $scope.title = settings.title;
                    $scope.message = settings.message;
                    $scope.type = settings.type;
                    $scope.okDisplay = settings.okDisplay;
                    $scope.cancelDisplay = settings.cancelDisplay;
                    $scope.okText = settings.okText;
                    $scope.cancelText = settings.cancelText;
                    $scope.ok = function () {
                        settings.ok($modalInstance);
                    };
                    $scope.cancel = function () {
                        settings.cancel($modalInstance);
                    };
                },
                size: settings.size
            })
        };

        this.info = function (userSettings) {
            var settings = angular.extend({}, defaultSettings, userSettings);
            settings.cancelDisplay = false;
            settings.cancel = null;
            settings.type = "info";
            messgae(settings);
        };

        this.confirm = function (userSettings) {
            var settings = angular.extend({}, defaultSettings, userSettings);
            settings.type = "confirm";
            messgae(settings);
        };
    });
})();
