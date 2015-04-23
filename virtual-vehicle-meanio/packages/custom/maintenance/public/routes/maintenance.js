'use strict';

angular.module('mean.maintenance').config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider.state('maintenance example page', {
            url        : '/maintenance/example',
            templateUrl: 'maintenance/views/index.html'
        });
    }
]);

'use strict';

//Setting up route
angular.module('mean.maintenance').config(['$stateProvider',
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
            .state('all maintenance records', {
                url        : '/virtual/maintenance/records',
                templateUrl: './maintenance/views/list.html',
                //controller : 'MaintenanceController',
                resolve    : {
                    loggedin: checkLoggedin
                }
            })
            .state('create maintenance record', {
                url        : '/virtual/maintenance/records/create',
                templateUrl: './maintenance/views/create.html',
                resolve    : {
                    loggedin: checkLoggedin
                }
            })
            .state('edit maintenance record', {
                url        : '/virtual/maintenance/record/:maintenanceId/edit',
                templateUrl: './maintenance/views/edit.html',
                resolve    : {
                    loggedin: checkLoggedin
                }
            })
            .state('maintenance by id', {
                url        : '/virtual/maintenance/record/:maintenanceId',
                templateUrl: './maintenance/views/view.html',
                resolve    : {
                    loggedin: checkLoggedin
                }
            });
    }
]);
