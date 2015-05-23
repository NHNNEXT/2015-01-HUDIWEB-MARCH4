(function() {
	'use strict';
	march4.app.registerController('signupController', function($scope, $http, $location, UserService, ToolTip) {
		$scope.user ={};
        $scope.signup = signup;
       
        function signup(){
        	$scope.loading = true;
        	UserService.Create($scope.user)
	        	.then(function(response){
	        		if(response.success){ //result by type of tootTip
	        			console.log("success of data response ");
	        			ToolTip.Success("Registration successful", true);
                        $location.path('/signin');
	        		} else{
	        			ToolTip.Error(response.message);
	        			$scope.loading = false;
	        			console.log("error of data response ");
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
