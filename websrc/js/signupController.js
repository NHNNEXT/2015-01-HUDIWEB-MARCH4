(function() {
	'use strict';
	march4.app.registerController('signupController', function($scope, $http, $location, UserService, ToolTip) {
		$scope.newUser = {};


        $scope.signup = function(){
        	$scope.loading = true;
        	UserService.create($scope.newUser)
        	.success(function(response){
        		console.log("success of data response ");
    			ToolTip.Success("Registration successful", true);
    			$location.path('/signin');
        	})
	        .error(function(response, status, headers, config){
                if (status == 400) {
                    var alertString = "";
                    for (var i = 0; i < response.length; i++)
                        alertString += response[i]+"\n";
                    
                    ToolTip.Error(alertString);
        			$scope.loading = false;
        			console.log("error of data response ");
                } else {
                	ToolTip.Error('Unexpected server error.');
                	$scope.loading = false;
	            }
            });
        } 
	});
}());
