(function () {
    'use strict';

    function addControllerJs(){
        return {
            load: function($q, $route, $rootScope) {
                var deferred = $q.defer();
                var controllerName = $route.current.$$route.controller;
                $.getScript('/js/'+controllerName+'.js',function(){
                    deferred.resolve();
                });

                return deferred.promise;
            }
        };
    }


    march4.app = angular.module('march4', ['ngRoute'], function($controllerProvider){
        
        // YG: 앵귤러에서 제공하는 $blabla를 밖에서 접근 가능하게 보여주는 것이 좋은 방법은 아닌 것 같습니다.
        march4.app.$controllerProvider = $controllerProvider;
        // YG: 이런 방식은 어떨까요?
        // march4.app.registerController = function(name, controller) {
        //     $controllerProvider.register(name, controller);
        // }
    });

    march4.app.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
        $routeProvider
            .when('/',{
                templateUrl: '/div/frontpage',
                controller: 'frontpageController',
                resolve: addControllerJs()
            })
            .when('/world', {
                templateUrl: '/div/world',
                controller: 'worldController',
                resolve: addControllerJs()
            })
            .when('/world/:worldId', {
                templateUrl: '/div/world',
                controller: 'worldController',
                resolve: addControllerJs()
            })
            .when('/building', {
                templateUrl: '/div/building',
                controller: 'buildingController',
                resolve: addControllerJs()
            })
            .when('/dummy/:dummyId', {
                templateUrl: '/div/dummy',
                controller: 'dummyController',
                resolve: addControllerJs()
            })
            .otherwise({
                redirectTo: document.location.pathname
            });


        $locationProvider.html5Mode(true).hashPrefix('!');      
    }]);
}());
