'use strict';

/**
 * @ngdoc service
 * @name virtualVehicleApp.vehicle
 * @description
 * # vehicle
 * Service in the virtualVehicleApp.
 */
angular.module('virtualVehicleApp')
  .factory('vehicleFactory', function ($resource, vehicleConfig) {
    return $resource(vehicleConfig.url + ':' + vehicleConfig.port + '/virtual/vehicles');
  });
