var formApp = angular.module('BuildingApp', []);
formApp.controller('BuildingController', [
  '$scope', '$window', '$http',
  function ($scope, $window, $http) {
        $scope.data = {};
        $scope.addData = {};
        $scope.delData = {};

        //빌딩의 소유자를 보낸다. 
        $scope.default = function () {
            $http({
                method: 'POST',
                url: '/building/default',
                data: $scope.data
            }).
            success(function (data, status, headers, config) {
                //$window.location.replace('/dummy/ajax');
                $scope.Buildings = data;
            }).
            error(function (data, status, headers, config) {
                if (status == 400) {
                    $scope.messages = data;
                } else {
                    alert('Unexpected server error.');
                }
            });
        };

        $scope.add = function () {
            $http({
                method: 'POST',
                url: '/building/add',
                data: $scope.addData
            }).
            success(function (data, status, headers, config) {
                //$window.location.replace('/dummy/ajax');
                $scope.default();
                //$scope.Dummies = data;
            }).
            error(function (data, status, headers, config) {
                if (status == 400) {
                    $scope.messages = data;
                } else {
                    alert('Unexpected server error.');
                }
            });
        };
      
        $scope.del = function (pid) {
            $scope.delData.pid = pid;
            $http({
                method: 'POST',
                url: '/building/del',
                data: $scope.delData
            }).
            success(function (data, status, headers, config) {
                //$window.location.replace('/dummy/ajax');
                $scope.default();
                //$scope.Dummies = data;
            }).
            error(function (data, status, headers, config) {
                if (status == 400) {
                    $scope.messages = data;
                } else {
                    alert('Unexpected server error.');
                }
            });
        };
}]);