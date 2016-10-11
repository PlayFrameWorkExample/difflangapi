/**
 * Created by acer on 10/11/2016.
 */
var app = angular.module('difflang', []);
        app.controller('hospitalController', function($scope) {
            $scope.firstName = "John";
            $scope.lastName = "Doe";
            $scope.fullName = function() {
                return $scope.firstName + " " + $scope.lastName;
            };
});