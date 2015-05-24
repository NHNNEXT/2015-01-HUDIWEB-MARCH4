(function() {
	'use strict';
	march4.app.registerController('signupController', function($scope, $http, $location, UserService, ToolTip) {
		$scope.user ={};
        $scope.signup = signup;
       
        function signup(){
        	$scope.loading = true;
        	UserService.Create($scope.user)
	        	.then(function(response){
	        		if(typeof response.data === 'object'){ //typeof $scope.toolTip
	        			ToolTip.Error(response.data);
	        			$scope.loading = false;
	        			console.log("error of data response ");
	        		} else{
	        			console.log("success of data response ");
	        			ToolTip.Success("Registration successful", true);
	        			$location.path('/signin');
	        		}
	        	});
        } 
	});
}());
