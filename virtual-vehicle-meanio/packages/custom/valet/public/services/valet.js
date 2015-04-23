'use strict';

angular.module('mean.valet').factory('Valet', [
    function ($http) {
        return {
            getVehicles: function () {
                var urlBase = '/virtual/valet/transactions',
                    callbackName = 'JSON_CALLBACK',
                    url = urlBase + '?callback=' + callbackName;

                return $http.jsonp(url);
            }
        };
    }
]);
