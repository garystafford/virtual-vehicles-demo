'use strict';

angular.module('mean.maintenance').factory('Maintenance', [
    function ($http) {
        return {
            getVehicles: function () {
                var urlBase = '/virtual/maintenance/records',
                    callbackName = 'JSON_CALLBACK',
                    url = urlBase + '?callback=' + callbackName;

                return $http.jsonp(url);
            }
        };
    }
]);
