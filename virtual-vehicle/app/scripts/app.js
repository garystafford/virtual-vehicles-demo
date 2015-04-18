'use strict';

/**
 * @ngdoc overview
 * @name virtualVehicleApp
 * @description
 * # virtualVehicleApp
 *
 * Main module of the application.
 */
angular
  .module('virtualVehicleApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller : 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller : 'AboutCtrl'
      })
      .when('/vehicle', {
        templateUrl: 'views/vehicle.html',
        controller : 'VehicleCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  })
  .constant('vehicleConfig', {
    'url' : 'http://localhost',
    'port': '8581'
  });
