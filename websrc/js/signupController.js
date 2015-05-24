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
	        			alert("error of data response ");
	        		} else{
	        			alert("success of data response ");
	        			ToolTip.Success("Registration successful", true);
	        			$location.path('/signin');
	        		}
	        		
//	        		if(response.status == 200){
//	        			//error
//	        			if(response.data !== ""){
//	                    	alert('you failed!');
//	                        //tooltip.Error(response.message);
//	                        $scope.loading = false;
//	                    
//	                    //success
//	                    } else {
//	                    	var errors = response.data;
//	        				alert('success!');
//	        				//tooltip.Success('Registration successful', true);
//	                        $location.path('/signin');
//	                    }
//	        		}else{
//	        			//do something	
//	        		}
	        	});
        } 
	});
}());
