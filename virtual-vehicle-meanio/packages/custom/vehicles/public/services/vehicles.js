'use strict';

//Vehicles service used for vehicles REST endpoint
angular.module('mean.vehicles').factory('Vehicles', ['$http',
    function($http) {
        return {
            getVehicles: function () {
                var urlBase = '/virtual/vehicles',
                    callbackName = 'JSON_CALLBACK',
                    url = urlBase + '?callback=' + callbackName;

                return $http.jsonp(url);
            }
        };
    }
]);