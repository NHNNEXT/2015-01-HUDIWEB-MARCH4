(function() {
	'use strict';
	march4.app.registerController('signinController', function($scope, $http,
			$location, $rootScope, UserService, ToolTip) {
		$scope.loginUser = {};
		
		$scope.signin = function () {
			$scope.loading = true;
			
			UserService.getByUser($scope.loginUser)
			.success(function(){
				ToolTip.Success("sign in successful", true);
				$rootScope.getUser();
    			$location.path('/');
    			console.log("succeed in login");
			})
			.error(function (response, status, headers, config) {
					if (status == 400) {
						var alertString = "";
						for (var i = 0; i < response.length; i++)
							alertString += response[i]+"\n";
						
						ToolTip.Error(alertString);
	        			$scope.loading = false;
						console.log("didn't sign in");
					}else{
						ToolTip.Error('Unexpected server error.');
					}
			});
		}
	});
}());