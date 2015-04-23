'use strict';

/* jshint -W098 */
angular.module('mean.valet').controller('ValetController', ['$scope', '$stateParams', '$location', 'Global', 'Valet',
    function ($scope, $stateParams, $location, Global, Valet) {
        $scope.global = Global;
        $scope.hasAuthorization = function (valet) {
            if (!valet || !valet.user) return false;
            return $scope.global.isAdmin || valet.user._id === $scope.global.user._id;
        };

        $scope.create = function (isValid) {
            if (isValid) {
                var valet = new Valet({
                    title  : this.title,
                    content: this.content
                });
                valet.$save(function (response) {
                    $location.path('valet/' + response._id);
                });

                this.title = '';
                this.content = '';
            } else {
                $scope.submitted = true;
            }
        };

        $scope.remove = function (valet) {
            if (valet) {
                valet.$remove(function (response) {
                    for (var i in $scope.valet) {
                        if ($scope.valet[i] === valet) {
                            $scope.valet.splice(i, 1);
                        }
                    }
                    $location.path('valet');
                });
            } else {
                $scope.valet.$remove(function (response) {
                    $location.path('valet');
                });
            }
        };

        $scope.update = function (isValid) {
            if (isValid) {
                var valet = $scope.valet;
                if (!valet.updated) {
                    valet.updated = [];
                }
                valet.updated.push(new Date().getTime());

                valet.$update(function () {
                    $location.path('valet/' + valet._id);
                });
            } else {
                $scope.submitted = true;
            }
        };

        $scope.find = function () {
            $scope.valet = {};
            Valet.getValet()
                .then(function (valet) {
                    $scope.valet = valet.data.valet;
                });
        };

        $scope.findOne = function () {
            Valet.get({
                valetId: $stateParams.valetId
            }, function (valet) {
                $scope.valet = valet;
            });
        };
    }
]);
