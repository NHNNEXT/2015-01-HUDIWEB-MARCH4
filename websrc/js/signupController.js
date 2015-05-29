(function() {
	'use strict';
	march4.app.registerController('signupController', function($scope, $http, $location, UserService) {
		$scope.user ={};
        $scope.signUp = function(){
        	$scope.dataLoading = true;
        	UserService.create($scope.user)
        	.success(function(response){
				var errors = response.data;
				alert('회원가입이 완료되었습니다.');
                $location.path('/signin');
        	})
	        .error(function(response, status, headers, config){
                if (status == 400) {
                	var alertString = "";
                    for (var i = 0; i < response.length; i++)
                        alertString += response[i]+"\n";

                    alert(alertString); 
                	$scope.dataLoading = false;
	            } else {
	                alert('Unexpected server error.');
	            }
            });
        } 
	});
}());
