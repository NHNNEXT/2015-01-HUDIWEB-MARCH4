(function() {
	'use strict';

	function addControllerJs() {
		return {
			load : function($q, $route, $rootScope) {
				var deferred = $q.defer();
				var controllerPath = '/js/';
				var controllerName = $route.current.$$route.controller;

				march4.util.addScript(controllerPath + controllerName + '.js',
						function() {
							deferred.resolve();
						});

				return deferred.promise;
			}
		};
	}

	march4.app.config([ '$routeProvider', '$locationProvider', '$httpProvider', '$provide',
			function($routeProvider, $locationProvider, $httpProvider, $provide) {
				$routeProvider.when('/', {
					templateUrl : '/div/frontpage',
					controller : 'frontpageController',
					resolve : addControllerJs()
				}).when('/world/:worldId?', {
					templateUrl : '/div/world',
					controller : 'worldController',
					resolve : addControllerJs()
				}).when('/building/:buildingId?/:panel?/:panelId?', {
					templateUrl : '/div/building',
					controller : 'buildingController',
					resolve : addControllerJs()
				}).when('/projects/:pId/quests', {
					templateUrl : '/div/roadmap',
					controller : 'roadmapController',
					resolve : addControllerJs()
				}).when('/dummy/:dummyId?/:panel?/:panelId?', {
					templateUrl : '/div/dummy',
					controller : 'dummyController',
					resolve : addControllerJs()
				}).when('/signup', {
					templateUrl : '/div/signup',
					controller : 'signupController',
					resolve : addControllerJs()
				}).when('/signin', {
					templateUrl : '/div/signin',
					controller : 'signinController',
					resolve : addControllerJs()
				}).when('/signout', {
					templateUrl : '/div/signout',
					controller : 'signoutController',
					resolve : addControllerJs()
				}).when('/errorPage/:errorNumber?', {
					templateUrl : '/div/errorPage/',
					controller : 'errorController',
					resolve : addControllerJs()
				}).otherwise({
					redirectTo : document.location.pathname
				});

				$locationProvider.html5Mode(true).hashPrefix('!');
				$httpProvider.interceptors.push('HttpInterceptor');
				$provide.decorator('$templateRequest', function($delegate) {
					var mySilentProvider = function(tpl, ignoreRequestError) {
					  return $delegate(tpl, true);
					};
					return mySilentProvider;
				});
			}]);

	march4.app.run([ '$route', '$rootScope', '$location',
		function($route, $rootScope, $location) {
			march4.util.setPathNoReloading = function(path) {
				var lastRoute = $route.current;
				var un = $rootScope.$on('$locationChangeSuccess',
						function() {
							$route.current = lastRoute;
							un();
						});
				return $location.path(path);
			};
		}
	]);
	
	march4.app.factory('HttpInterceptor', function ($q,$location) {
	    return {
	        response: function (response) {
				//do something
	            return response;
	        },
	        responseError: function (rejection) {
	        	if(rejection.status === 401)
                	$location.path('/errorPage/'+rejection.status);
	            return $q.reject(rejection);
	        }
	    };
	});
}());