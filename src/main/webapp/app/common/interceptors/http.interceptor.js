/**
 * Created by yang_shoulai on 2016/6/26.
 */
(function () {

    angular.module('app').factory('HttpInterceptor', ['$q', '$injector', function ($q, $injector) {
        return {
            responseError: function (response) {
                //用户会话超时
                if (response.status == 401) {
                    $injector.get('$rootScope').UserInfo = null;
                    $injector.get('$rootScope').$broadcast('userStateChange', null);
                    $injector.get('UserService').showLoginModal();
                }
                return $q.reject(response);
            }
        };
    }]);

})();