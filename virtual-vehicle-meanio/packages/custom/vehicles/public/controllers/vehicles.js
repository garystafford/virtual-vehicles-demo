'use strict';

angular.module('mean.vehicles').controller('VehiclesController', ['$scope', '$stateParams', '$location', 'Global', 'Vehicles',
    function ($scope, $stateParams, $location, Global, Vehicles) {
        $scope.global = Global;
        $scope.hasAuthorization = function (vehicle) {
            if (!vehicle || !vehicle.user) return false;
            return $scope.global.isAdmin || vehicle.user._id === $scope.global.user._id;
        };

        $scope.create = function (isValid) {
            if (isValid) {
                var vehicle = new Vehicles({
                    title  : this.title,
                    content: this.content
                });
                vehicle.$save(function (response) {
                    $location.path('vehicles/' + response._id);
                });

                this.title = '';
                this.content = '';
            } else {
                $scope.submitted = true;
            }
        };

        $scope.remove = function (vehicle) {
            if (vehicle) {
                vehicle.$remove(function (response) {
                    for (var i in $scope.vehicles) {
                        if ($scope.vehicles[i] === vehicle) {
                            $scope.vehicles.splice(i, 1);
                        }
                    }
                    $location.path('vehicles');
                });
            } else {
                $scope.vehicle.$remove(function (response) {
                    $location.path('vehicles');
                });
            }
        };

        $scope.update = function (isValid) {
            if (isValid) {
                var vehicle = $scope.vehicle;
                if (!vehicle.updated) {
                    vehicle.updated = [];
                }
                vehicle.updated.push(new Date().getTime());

                vehicle.$update(function () {
                    $location.path('vehicles/' + vehicle._id);
                });
            } else {
                $scope.submitted = true;
            }
        };

        $scope.find = function () {
            $scope.vehicles = {};
            Vehicles.getVehicles()
                .then(function (vehicles) {
                    $scope.vehicles = vehicles.data.vehicles;
                });
        };

        $scope.findOne = function () {
            Vehicles.get({
                vehicleId: $stateParams.vehicleId
            }, function (vehicle) {
                $scope.vehicle = vehicle;
            });
        };
    }
]);
