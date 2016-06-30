/**
 * Created by yang_shoulai on 2016/6/25.
 */

(function () {
    angular.module("app").service('CookieService', ['$cookieStore', function ($cookieStore) {

        this.KEY_CONSTANT = {
            user_login_remember_me: 'user_login_remember_me', //是否记住用户登录状态 ：1 记住
            user_login_username: 'user_login_username',
            user_login_password: 'user_login_password',
            album_last_visited: 'album_last_visited'
        };

        this.put = function (key, value) {
            $cookieStore.put(key, value);
        };
        this.remove = function (key) {
            $cookieStore.remove(key);
        };

        this.get = function (key) {
            return $cookieStore.get(key);
        }

    }]);
})();
