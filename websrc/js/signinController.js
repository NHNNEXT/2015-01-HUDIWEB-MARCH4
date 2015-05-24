(function() {
	'use strict';
	march4.app.registerController('signinController', function($scope, $http,
			$location, UserService, ToolTip) {
		$scope.user = {};

		$scope.signin = signin;

		function signin() {
			$scope.loading = true;
			UserService.GetByUser($scope.user).then(function(response) {
				if (!(response.data === true)) {
        			ToolTip.Error(response.data);
        			$scope.loading = false;
					console.log("didn't sign in");
				} else {
        			ToolTip.Success("sign in successful", true);		
        			$location.path('/');
					console.log("succeed in login");
				}
			});
		}
	});
}());