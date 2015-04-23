'use strict';

angular.module('mean.maintenance').controller('MaintenanceController', ['$scope', '$stateParams', '$location', 'Global', 'Maintenance',
    function ($scope, $stateParams, $location, Global, Maintenance) {
        $scope.global = Global;
        $scope.hasAuthorization = function (maintenance) {
            if (!maintenance || !maintenance.user) return false;
            return $scope.global.isAdmin || maintenance.user._id === $scope.global.user._id;
        };

        $scope.create = function (isValid) {
            if (isValid) {
                var maintenance = new Maintenance({
                    title  : this.title,
                    content: this.content
                });
                maintenance.$save(function (response) {
                    $location.path('maintenance/' + response._id);
                });

                this.title = '';
                this.content = '';
            } else {
                $scope.submitted = true;
            }
        };

        $scope.remove = function (maintenance) {
            if (maintenance) {
                maintenance.$remove(function (response) {
                    for (var i in $scope.maintenance) {
                        if ($scope.maintenance[i] === maintenance) {
                            $scope.maintenance.splice(i, 1);
                        }
                    }
                    $location.path('maintenance');
                });
            } else {
                $scope.maintenance.$remove(function (response) {
                    $location.path('maintenance');
                });
            }
        };

        $scope.update = function (isValid) {
            if (isValid) {
                var maintenance = $scope.maintenance;
                if (!maintenance.updated) {
                    maintenance.updated = [];
                }
                maintenance.updated.push(new Date().getTime());

                maintenance.$update(function () {
                    $location.path('maintenance/' + maintenance._id);
                });
            } else {
                $scope.submitted = true;
            }
        };

        $scope.find = function () {
            $scope.maintenance = {};
            Maintenance.getMaintenance()
                .then(function (maintenance) {
                    $scope.maintenance = maintenance.data.maintenance;
                });
        };

        $scope.findOne = function () {
            Maintenance.get({
                maintenanceId: $stateParams.maintenanceId
            }, function (maintenance) {
                $scope.maintenance = maintenance;
            });
        };
    }
]);
