'use strict';

/**
 * @ngdoc function
 * @name virtualVehicleApp.controller:VehicleCtrl
 * @description
 * # VehicleCtrl
 * Controller of the virtualVehicleApp
 */
angular.module('virtualVehicleApp')
  .controller('VehicleCtrl', function ($scope, vehicleFactory) {
    $scope.vehicles = {};
    vehicleFactory.get(function (data) {
      $scope.vehicles = data._embedded.vehicles;
    });
  });



