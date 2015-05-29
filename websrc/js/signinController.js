(function() {
	'use strict';
	march4.app.registerController('signinController', function($scope, $http,
			$location, UserService, ToolTip) {//'$cookieStore'
		$scope.user = {};
		//$rootScope.session = {};
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
        			
//        			$cookieStore.put('email',$scope.user);
//        			$rootScope.session = $cookieStore.get('email');
        			
        			$location.path('/');
					console.log("succeed in login");
				}
			});
		}
		
	});
}());