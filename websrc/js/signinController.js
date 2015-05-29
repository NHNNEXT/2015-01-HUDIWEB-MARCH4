(function() {
	'use strict';
	march4.app.registerController('signinController', function($scope, $http, $location, $rootScope, UserService) {
		$scope.loginUser ={};
		console.log($scope.user);
		$scope.signIn = function () {
			$scope.loading = true;
			UserService.signin($scope.loginUser)
			.success(function(){
				$rootScope.getUser();
				$location.path('/');
			})
			.error(function (response, status, headers, config) {
				if (status == 400) {
					var alertString = "";
					for (var i = 0; i < response.length; i++)
						alertString += response[i]+"\n";

					alert(alertString);
					$scope.loading = false;
				} else {
					alert('Unexpected server error.');
				}
			});
		};
	});
}());