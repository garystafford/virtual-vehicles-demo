'use strict';

//Setting up route
angular.module('mean.vehicles').config(['$stateProvider',
    function($stateProvider) {
        // Check if the user is connected
        var checkLoggedin = function($q, $timeout, $http, $location) {
            // Initialize a new promise
            var deferred = $q.defer();

            // Make an AJAX call to check if the user is logged in
            $http.get('/loggedin').success(function(user) {
                // Authenticated
                if (user !== '0') $timeout(deferred.resolve);

                // Not Authenticated
                else {
                    $timeout(deferred.reject);
                    $location.url('/login');
                }
            });

            return deferred.promise;
        };

        // states for my app
        $stateProvider
            .state('all vehicles', {
                url: '/virtual/vehicles',
                templateUrl: 'vehicles/views/list.html',
                //controller : 'VehiclesController',
                resolve: {
                    loggedin: checkLoggedin
                }
            })
            .state('create vehicle', {
                url: '/virtual/vehicles/create',
                templateUrl: 'vehicles/views/create.html',
                resolve: {
                    loggedin: checkLoggedin
                }
            })
            .state('edit vehicle', {
                url: '/virtual/vehicles/:vehicleId/edit',
                templateUrl: 'vehicles/views/edit.html',
                resolve: {
                    loggedin: checkLoggedin
                }
            })
            .state('vehicle by id', {
                url: '/virtual/vehicles/:vehicleId',
                templateUrl: 'vehicles/views/view.html',
                resolve: {
                    loggedin: checkLoggedin
                }
            });
    }
]);
