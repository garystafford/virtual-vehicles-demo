'use strict';

//Setting up route
angular.module('mean.valet').config(['$stateProvider',
    function ($stateProvider) {
        // Check if the user is connected
        var checkLoggedin = function ($q, $timeout, $http, $location) {
            // Initialize a new promise
            var deferred = $q.defer();

            // Make an AJAX call to check if the user is logged in
            $http.get('/loggedin').success(function (user) {
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
            .state('all valet transactions', {
                url        : '/virtual/valet/transactions',
                templateUrl: './valet/views/list.html',
                //controller : 'ValetController',
                resolve    : {
                    loggedin: checkLoggedin
                }
            })
            .state('create valet transaction', {
                url        : '/virtual/valet/transactions/create',
                templateUrl: './valet/views/create.html',
                resolve    : {
                    loggedin: checkLoggedin
                }
            })
            .state('edit valet transaction', {
                url        : '/virtual/valet/transaction/:valetId/edit',
                templateUrl: './valet/views/edit.html',
                resolve    : {
                    loggedin: checkLoggedin
                }
            })
            .state('valet transaction by id', {
                url        : '/virtual/valet/transaction/:vehicleId',
                templateUrl: './valet/views/view.html',
                resolve    : {
                    loggedin: checkLoggedin
                }
            });
    }
]);
