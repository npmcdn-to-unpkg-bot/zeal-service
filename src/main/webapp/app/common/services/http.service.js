/**
 * Created by yang_shoulai on 2016/6/25.
 */
(function () {
    angular.module('zeal-common').service('HttpService', ['$q', '$injector', '$http', '$rootScope',
        function ($q, $injector, $http, $rootScope) {
            this.http = function (params) {
                var defer = $q.defer();
                $http(params).success(function (data) {
                    if (data.status == 1) {
                        defer.resolve(data.result);
                    } else if (data.status == 2 || data.status == 3 || data.status == 5) {
                        defer.reject({message: data.message});
                    } else if (data.status == 4) {
                        $rootScope.UserInfo = null;
                        $rootScope.$broadcast('userStateChange', null);
                        defer.reject({message: "用户会话过期，请重新登录！"});
                        //$injector.get('UserService').showLoginModal();
                    }
                }).error(function (data) {
                    defer.reject({message: "服务器响应错误"});
                });

                return {
                    promise: defer.promise,
                    success: function (success) {
                        this.promise.then(success);
                        return this;
                    },
                    error: function (error) {
                        this.promise.then(null, error);
                        return this;
                    }
                };
            };
        }]);

})();